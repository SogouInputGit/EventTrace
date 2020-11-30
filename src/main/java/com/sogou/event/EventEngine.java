package com.sogou.event;

import com.google.gson.Gson;
import com.sogou.event.internal.Preconditions;
import com.sogou.event.operation.EventDefineFactory;
import com.sogou.event.operation.IGsonOperation;
import com.sogou.event.operation.IOperation;
import com.sogou.event.operation.EventOperationBean;
import com.sogou.event.internal.IEventStorage;
import com.sogou.event.internal.EventProto;
import com.sogou.event.internal.MemoryCache;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;

/**
 * @author dongjianye on 2020/6/5
 *
 * 处理每个指令的引擎，每个指令处理的要快
 */
class EventEngine {
    private Gson mGson = new Gson();

    private final IEventStorage mStorage;
    private final MemoryCache mMemory;
    private final EventBackup mBackup;
    private final Monitor mMonitor;

    EventEngine(final IEventStorage storage, final MemoryCache memory, final EventBackup backup,
                final Monitor monitor) {
        mStorage = storage;
        mMemory = memory;
        mBackup = backup;
        mMonitor = monitor;
    }

    /**
     * 是否立即处理了
     * @param event
     * @return 是否立即处理，如果立即处理的话
     */
    boolean process(@NonNull EventProto event) {
        // 每处理一个请求的时候，都需要给storage加锁，因为有可能正在发送
        synchronized (mMonitor.mStorageLock) {
            if (mMonitor.mIsSending) {
                mBackup.addToBackup(event);
                return false;
            } else {
                processInternal(event);
                event.recycle();
                if (BuildConfig.DEBUG) {
//                    Log.d("EventRecord", "process internal " + event);
                    mMonitor.removeSuspending();
                }
                return true;
            }
        }
    }

    private void processInternal(@NonNull EventProto event) {
        // 从内存当中取值
        final String key = event.getKey();
        final EventOperationBean commandRecord = EventDefineFactory.getCommandRecord(mMonitor.getId(), key);
        if (commandRecord == null) {
            return;
        }

        final Class<?> clazz = commandRecord.getClazz();
        final Type type = commandRecord.getType();
        final IOperation operation = commandRecord.getOperation();
        final boolean useMemoryCache = operation.useMemoryCache();

        if (!useMemoryCache) {
            // 不带内存缓存，直接操作文件存储即可
            try {
                final Object value = operation.getFromStorage(key, mStorage);
                final Object newValue = operation.operate(value, event);
                operation.setToStorage(key, newValue, mStorage);
            } catch (Throwable e) {
                if (BuildConfig.DEBUG) {
                    Preconditions.throwCondition("EventEngine:processInternal:notUseMemoryCache " + key + " failed");
                }
                mMonitor.recordError(Monitor.ERROR_NONE_USE_MEMORY_CACHE_ERROR);
            }
            return;
        }

        Object value = mMemory.get(key);

        if (value == null) {
            // 缓存没有，需要从main里面去读
            if (operation instanceof IGsonOperation) {
                final String storageValue = (String) operation.getFromStorage(key, mStorage);
                try {
                    value = ((IGsonOperation) operation).deserialize(mGson, type, storageValue);
                } catch (Throwable ignored) {
                    if (BuildConfig.DEBUG) {
                        Preconditions.throwCondition("EventEngine:processInternal:useMemoryCache: " + key + " deserialize failed");
                    }
                    mMonitor.recordError(Monitor.ERROR_DESERIALIZE_FROM_STORAGE_ERROR);
                }
            } else {
                // 非gson类的pignback，直接读取
                value = operation.getFromStorage(key, mStorage);
            }
        }

        if (value == null) {
            try {
                value = clazz.newInstance();
            } catch (Throwable ignored) {
                value = null;
                mMonitor.recordError(Monitor.ERROR_NEW_CLAZZ_INSTANCE);
                if (BuildConfig.DEBUG) {
                    Preconditions.throwCondition("EventEngine:processInternal:newClazzInstance: " + key + " failed");
                }
            }
        }

        if (value == null) {
            mMonitor.recordError(Monitor.ERROR_FINAL_VALUE_NULL);
            if (BuildConfig.DEBUG) {
                Preconditions.throwCondition("EventEngine:processInternal:value is: " + key + " failed");
            }
            return;
        }

        Object updateMemoryResult;
        try {
            updateMemoryResult = operation.operate(value, event);
            mMemory.put(key, updateMemoryResult);
        } catch (Throwable ignored) {
            mMonitor.recordError(Monitor.ERROR_OPERATE);
            if (BuildConfig.DEBUG) {
                Preconditions.throwCondition("EventEngine:processInternal:operate: " + key + " failed");
            }
            return;
        }

        if (operation instanceof IGsonOperation) {
            try {
                final Object updateStorageValue = ((IGsonOperation) operation).serialize(mGson, clazz, updateMemoryResult);
                operation.setToStorage(key, updateStorageValue, mStorage);
            } catch (Throwable e) {
                mMonitor.recordError(Monitor.ERROR_SAVE_TO_STORAGE_1);
                if (BuildConfig.DEBUG) {
                    Preconditions.throwCondition("EventEngine:processInternal:saveToStorage1: " + key + " failed");
                }
            }
        } else {
            try {
                operation.setToStorage(key, updateMemoryResult, mStorage);
            } catch (Throwable ignored) {
                mMonitor.recordError(Monitor.ERROR_SAVE_TO_STORAGE_2);
                if (BuildConfig.DEBUG) {
                    Preconditions.throwCondition("EventEngine:processInternal:saveToStorage2: " + key + " failed");
                }
            }

        }
    }
}
