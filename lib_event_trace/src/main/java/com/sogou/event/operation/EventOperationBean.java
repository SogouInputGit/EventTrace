package com.sogou.event.operation;

import androidx.annotation.NonNull;

import com.sogou.event.internal.Preconditions;

import java.lang.reflect.Type;

/**
 * @author dongjianye
 */
public class EventOperationBean {
    private final Type mType;   // 给gson用的，用来将string等类型生成用户自定义的一些结构，如果是普通类型，则不需要这个值
    private final Class mClazz; // 用来生成object的
    private final IOperation mOperation;    // 实际的操作类，需要保证用户自定义的Operation只有一个

    @SuppressWarnings("unchecked")
    EventOperationBean(final Type type, final Class clazz, final IOperation operation) {
        mType = type;
        mClazz = clazz;
        mOperation = operation;
    }

    public Class getClazz() {
        return mClazz;
    }

    public @NonNull
    IOperation getOperation() {
        if (mOperation == null) {
            Preconditions.throwCondition("EventOperationBean mOperation is null");
        }
        return mOperation;
    }

    public Type getType() {
        return mType;
    }

}
