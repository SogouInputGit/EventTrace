package com.sogou.event.operation;

import com.sogou.event.internal.IEventStorage;
import com.sogou.event.internal.EventProto;
import com.sogou.event.internal.Preconditions;

/**
 * @author dongjianye on 2020/6/5
 */
public final class LongOperation extends BaseNumberOperation<Long> {

    @Override
    public Long increase(Long object, int step) {
        return step + (object == null ? 0 : object);
    }

    @Override
    public Long operate(Long object, EventProto event) {
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
    public Long getFromStorage(String key, IEventStorage storage) {
        return storage.getLong(key);
    }

    @Override
    public void setToStorage(String key, Long value, IEventStorage storage) {
        storage.putLong(key, value);
    }

    @Override
    public boolean useMemoryCache() {
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
