package com.sogou.event.operation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongjianye
 */
public abstract class BaseEventDefine {

    private final Map<String, Class<? extends IOperation>> mDefines = new HashMap<>();

    public final Map<String, Class<? extends IOperation>> getRecordDefines() {
        return mDefines;
    }

    protected final void putEventDefine(final String key, final Class<? extends IOperation> clazz) {
        mDefines.put(key, clazz);
    }

    protected abstract void initDefines();

    public final void clear() {
        mDefines.clear();
    }
}
