package com.sogou.event.operation;

/**
 * @author dongjianye on 2020/6/9
 */
public interface OperationInstanceCreator<T> {

    public T createInstance(Class<?> clazz);
}
