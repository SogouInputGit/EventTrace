package com.sogou.event.operation;

import com.sogou.event.internal.EventProto;

import androidx.annotation.NonNull;

/**
 * @author dongjianye on 2020/6/5
 * 自定义的action，通常给用户自己定义的来使用
 */
public interface IAction<T> {
    public T action(T object, @NonNull EventProto event);
}
