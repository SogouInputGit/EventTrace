package com.sogou.event.operation;

import com.sogou.event.internal.IEventStorage;
import com.sogou.event.internal.Preconditions;
import com.sogou.event.internal.EventProto;

/**
 * @author dongjianye on 2020/6/5
 */
public class IntOperation extends BaseNumberOperation<Integer> {

    @Override
    protected Integer increase(Integer object, int step) {
        return step + (object == null ? 0 : object);
    }

    @Override
    public Integer operate(Integer object, EventProto event) {
        if (event == null) {
            Preconditions.throwCondition("IntOperation:operate:Event param is null");
            return object;
        }

        final int cmd = event.getEventType();
        switch (cmd) {
            case EventProto.EVENT_TYPE_INCREASE_KEY_STEP:
                return increase(object, event.getStep());
            default: {
                Preconditions.throwCondition("IntOperation:operate:unsupported event type");
                return object;
            }
        }
    }

    @Override
    public Integer getFromStorage(String key, IEventStorage storage) {
        return storage.getInt(key, 0);
    }

    @Override
    public void setToStorage(String key, Integer value, IEventStorage storage) {
        storage.putInt(key, value);
    }

    @Override
    public final boolean useMemoryCache() {
        return false;
    }

    @Override
    public String get(String key, IEventStorage storage) {
        return String.valueOf(getFromStorage(key, storage));
    }

    @Override
    public void reset(String key, IEventStorage storage) {
        storage.remove(key);
    }
}
