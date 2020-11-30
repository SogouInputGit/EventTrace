package com.sogou.event.operation;

import android.text.TextUtils;

import com.sogou.event.BuildConfig;
import com.sogou.event.internal.IEventStorage;
import com.sogou.event.internal.EventProto;
import com.sogou.event.internal.Preconditions;

import java.util.HashMap;

/**
 * @author dongjianye on 2020/6/5
 * 内置的，可以直接用的，必须用HashMap<String, ?> ? 必须为String或者为HashMap<String, ?>
 * 如果想用其他的，需要自己定义
 */
public class BaseMapOperation<T> extends BaseGsonOperation<T> implements IMapOperation<T>, IAction<T> {

    @Override
    public T increase(T object, String item, int step) {
        return increase(object, new String[]{item}, step);
    }


    @Override
    @SuppressWarnings("unchecked")
    public T increase(T object, String[] paths, int step) {
        if (paths == null || paths.length <= 0) {
            return object;
        }

        for (String path : paths) {
            if (TextUtils.isEmpty(path)) {
                if (BuildConfig.DEBUG) {
                    throw new RuntimeException("BaseMapOperation 传过来的路径为空");
                }
                return object;
            }
        }

        final int mapLayerCount = paths.length;
        HashMap<String, Object> curLayerValue = (HashMap<String, Object>) object;
        if (curLayerValue == null) {
            curLayerValue = new HashMap<>();
        }
        final HashMap<String, Object> result = curLayerValue;
        for (int i = 0; i < mapLayerCount; ++i) {
            final String key = paths[i];
            final boolean isLastLayer = i == mapLayerCount - 1;
            if (!isLastLayer) {
                HashMap<String, Object> nextLayerValue = (HashMap<String, Object>) curLayerValue.get(key);
                if (nextLayerValue == null) {
                    nextLayerValue = new HashMap<>();
                    curLayerValue.put(key, nextLayerValue);
                }
                curLayerValue = nextLayerValue;
            } else {
                String valueStr = (String) curLayerValue.get(key);
                long curValue;
                if (valueStr == null) {
                    curValue = 0L;
                } else {
                    try {
                        curValue = Long.parseLong(valueStr);
                    } catch (Exception e) {
                        if (BuildConfig.DEBUG) {
                            Preconditions.throwCondition("BaseMapOperation:ParseLong:error :" + e.getMessage());
                        }
                        curValue = 0L;
                    }
                }
                curLayerValue.put(key, String.valueOf(curValue + step));
            }
        }

        return (T) result;
    }

    @Override
    public final T operate(T object, EventProto event) {
        if (event == null) {
            Preconditions.throwCondition("BaseMapOperation:operate:Event param is null");
            return object;
        }

        final int cmd = event.getEventType();
        switch (cmd) {
            case EventProto.EVENT_TYPE_INCREASE_KEY_ITEM_STEP:
                return increase(object, event.getItem(), event.getStep());
            case EventProto.EVENT_TYPE_INCREASE_KEY_PATHS_STEP:
                return increase(object, event.getPaths(), event.getStep());
            case EventProto.EVENT_TYPE_ACTION:
                return action(object, event);
            default: {
                Preconditions.throwCondition("BaseMapOperation:operate:unsupported event type");
                return object;
            }
        }
    }

    @Override
    public T action(T object, EventProto event) {
        return object;
    }

    @Override
    public String getFromStorage(String key, IEventStorage storage) {
        return storage.getString(key);
    }

    @Override
    public void setToStorage(String key, String value, IEventStorage storage) {
        storage.putString(key, value);
    }

    @Override
    public final boolean useMemoryCache() {
        return true;
    }

    @Override
    public String get(String key, IEventStorage storage) {
        return getFromStorage(key, storage);
    }

    @Override
    public void reset(String key, IEventStorage storage) {
        storage.remove(key);
    }
}
