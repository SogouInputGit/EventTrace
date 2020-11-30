package com.sogou.event.operation;

import com.sogou.event.internal.IEventStorage;
import com.sogou.event.internal.EventProto;
import com.sogou.event.internal.Preconditions;

/**
 * @author dongjianye on 2020/6/5
 * 被外部继承的，可以直接用的
 */
public abstract class CustomGsonOperation<T> extends BaseGsonOperation<T> implements IAction<T> {

    @Override
    public T operate(T object, EventProto event) {
        if (event == null) {
            Preconditions.throwCondition("CustomGsonOperation:operate: event is null");
            return object;
        }

        if (event.getEventType() != EventProto.EVENT_TYPE_ACTION) {
            Preconditions.throwCondition("CustomGsonOperation:operate: event type is not EVENT_TYPE_ACTION");
            return object;
        }

        return action(object, event);
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
