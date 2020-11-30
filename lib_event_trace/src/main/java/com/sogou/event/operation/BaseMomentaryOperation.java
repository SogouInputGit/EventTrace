package com.sogou.event.operation;

import com.sogou.event.internal.EventProto;
import com.sogou.event.internal.IEventStorage;

/**
 * @author dongjianye on 2020/6/8
 */
public abstract class BaseMomentaryOperation implements IOperation<String, String> {

    @Override
    public final String operate(String object, EventProto event) {
        return null;
    }

    @Override
    public final String getFromStorage(String key, IEventStorage storage) {
        return null;
    }

    @Override
    public final void setToStorage(String key, String value, IEventStorage storage) {

    }

    @Override
    public final boolean useMemoryCache() {
        return false;
    }
}
