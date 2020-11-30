package com.sogou.event;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.sogou.event.internal.EventParamProto;
import com.sogou.event.internal.EventProto;
import com.sogou.event.internal.IEventStorage;
import com.sogou.event.internal.MemoryCache;
import com.sogou.event.operation.EventDefineFactory;
import com.sogou.event.operation.EventOperationBean;
import com.sogou.event.storage.MmkvStorage;
import com.tencent.mmkv.MMKV;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import androidx.annotation.VisibleForTesting;
import androidx.collection.ArrayMap;


/**
 * Public for the outside.
 *
 * @author dongjianye
 */
public class EventRecord {
    private static final int MSG_SEND_TIMEOUT = 1;
    private static final long SEND_TIMEOUT = 5 * 60 * 1000; // 5分钟吧

    public static final String ID_DEFAULT = "def";

    private final EventDispatcher mCollector;
    private final IEventStorage mStorage;
    private final MemoryCache mMemoryCache;
    private final EventEngine mEngine;
    private final EventBackup mBackup;
    private final Handler mSendFinishTimer;
    private final Monitor mMonitor;

    private volatile static EventRecord instance;

    private static final ArrayMap<String, EventRecord> sOthers = new ArrayMap<>();
    private static final ReentrantLock sOthersLock = new ReentrantLock();

    // 正常情况下，用这个就行了
    public static EventRecord getInstance(Context applicationContext) {
        if (instance == null) {
            synchronized (EventRecord.class) {
                if (instance == null) {
                    instance = new EventRecord(applicationContext, ID_DEFAULT);
                }
            }
        }
        return instance;
    }

    // 给其他id用的，目前是用来记录性能输入，因为可能字段比较多，怕发不上来，所以单独做一个，可以用pingback发送，也可以专门做个接口
    public static EventRecord getInstance(Context applicationContext, String id) {
        IdManager.checkId(id);

        sOthersLock.lock();

        try {
            EventRecord record = sOthers.get(id);
            if (record == null) {
                record = new EventRecord(applicationContext, id);
                sOthers.put(id, record);
            }
            return record;
        } finally {
            sOthersLock.unlock();
        }
    }

    private String getBackupDir(Context applicationContext, String id) {
        if (ID_DEFAULT.equals(id)) {
            String backupDir;
            try {
                backupDir = applicationContext.getFilesDir().getAbsolutePath() + "/foreign_event_backup/";
            } catch (Exception e) {
                backupDir = "/data/data/" + applicationContext.getPackageName() + "/files/foreign_event_backup/";
            }
            return backupDir;
        } else {
            return null;
        }
    }

    private EventRecord(final Context applicationContext, final String id) {
        mMonitor = new Monitor(id);
        mStorage = new MmkvStorage(applicationContext, id, MMKV.SINGLE_PROCESS_MODE);
        mMemoryCache = new MemoryCache();

        mBackup = new EventBackup(mMonitor, id, getBackupDir(applicationContext, id));
        mEngine = new EventEngine(mStorage, mMemoryCache, mBackup, mMonitor);
        mCollector = new EventDispatcher(mEngine, mBackup, mMonitor);
        mSendFinishTimer = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_SEND_TIMEOUT) {
                    if (BuildConfig.DEBUG) {
                        Log.d(Monitor.TAG, "finish send timeout");
                    }
                    mMonitor.recordError(Monitor.ERROR_SEND_TIMEOUT);
                    finishSend(false);
                }
            }
        };
        mCollector.start();
    }

    public String getEventRecords() {
        synchronized (mMonitor.mStorageLock) {
            if (mMonitor.mIsSending) {
                if (BuildConfig.DEBUG) {
                    throw new RuntimeException("get pingback when is sending");
                }
            }

            mSendFinishTimer.removeMessages(MSG_SEND_TIMEOUT);
            mSendFinishTimer.sendEmptyMessageDelayed(MSG_SEND_TIMEOUT, SEND_TIMEOUT);

            mMonitor.mIsSending = true;

            final Set<String> keySet = EventDefineFactory.getKeys(mMonitor.getId());

            StringBuilder sb = new StringBuilder();
            for (String key : keySet) {
                final EventOperationBean operationBean = EventDefineFactory.getCommandRecord(mMonitor.getId(), key);
                if (operationBean == null) {
                    continue;
                }
                String keyValue = operationBean.getOperation().get(key, mStorage);
                sb.append(key).append('=').append(keyValue).append('&');
            }

            if (sb.length() <= 0 || sb.charAt(sb.length() - 1) != '&') {
                sb.append('&');
            }

            sb.append("event_error=").append(mMonitor.getError());

            if (BuildConfig.DEBUG) {
                Log.d(Monitor.TAG, "getEventRecords end");
            }

            return sb.toString();
        }
    }

    public void finishSend(boolean suc) {
        synchronized (mMonitor.mStorageLock) {
            mSendFinishTimer.removeMessages(MSG_SEND_TIMEOUT);
            if (!mMonitor.mIsSending) {
                if (BuildConfig.DEBUG) {
                    throw new RuntimeException("must call getEventRecords before finishSend");
                }
                return;
            }

            if (suc) {
                final Set<String> keySet = EventDefineFactory.getKeys(mMonitor.getId());

                for (String key : keySet) {
                    final EventOperationBean operationBean = EventDefineFactory.getCommandRecord(mMonitor.getId(), key);
                    if (operationBean != null) {
                        operationBean.getOperation().reset(key, mStorage);
                    }
                }

                mMemoryCache.clear();

                mMonitor.clearError();
            }

            // 将备份重新发送
            final List<EventProto> backup = mBackup.getAndClearBackup();
            for (EventProto eventProto : backup) {
                mCollector.trackEvent(eventProto);
            }

            mMonitor.mIsSending = false;

            if (BuildConfig.DEBUG) {
                Log.d(Monitor.TAG, "finishSend end");
            }
        }
    }

    // 给数字用的
    public void increaseNumber(String key) {
        increaseNumber(key, 1);
    }

    public void increaseNumber(String key, int step) {
        EventProto eventProto = EventProto.obtain();
        eventProto.setEventType(EventProto.EVENT_TYPE_INCREASE_KEY_STEP);
        eventProto.setKey(key);
        eventProto.setStep(step);
        mCollector.trackEvent(eventProto);

        if (BuildConfig.DEBUG) {
            mMonitor.addSuspending();
        }
    }

    // 给单层map用的
    public void increaseOneMap(String key, String item) {
        increaseOneMap(key, item, 1);
    }

    public void increaseOneMap(String key, String item, int step) {
        EventProto eventProto = EventProto.obtain();
        eventProto.setEventType(EventProto.EVENT_TYPE_INCREASE_KEY_ITEM_STEP);
        eventProto.setKey(key);
        eventProto.setItem(item);
        eventProto.setStep(step);
        mCollector.trackEvent(eventProto);

        if (BuildConfig.DEBUG) {
            mMonitor.addSuspending();
        }
    }

    // 给多重map用的
    public void increaseMultiMap(String key, String[] paths) {
        increaseMultiMap(key, paths, 1);
    }

    public void increaseMultiMap(String key, String[] paths, int step) {
        EventProto eventProto = EventProto.obtain();
        eventProto.setEventType(EventProto.EVENT_TYPE_INCREASE_KEY_PATHS_STEP);
        eventProto.setPaths(paths);
        eventProto.setKey(key);
        eventProto.setStep(step);
        mCollector.trackEvent(eventProto);

        if (BuildConfig.DEBUG) {
            mMonitor.addSuspending();
        }
    }

    // 给自定义的JSON类型使用的
    public void customAction(String key, int actionType, EventParamProto param) {
        EventProto eventProto = EventProto.obtain();
        eventProto.setEventType(EventProto.EVENT_TYPE_ACTION);
        eventProto.setKey(key);
        eventProto.setActionType(actionType);
        eventProto.setParam(param);
        mCollector.trackEvent(eventProto);

        if (BuildConfig.DEBUG) {
            mMonitor.addSuspending();
        }
    }

    @VisibleForTesting
    public void waitDone() {
        mMonitor.waitDone();
    }
}

