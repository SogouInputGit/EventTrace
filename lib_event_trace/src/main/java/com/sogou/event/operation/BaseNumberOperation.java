package com.sogou.event.operation;

/**
 * @author dongjianye on 2020/6/5
 */
abstract class BaseNumberOperation<T> implements IOperation<T, T> {
    /**
     * Increase the record of the key.
     *
     * @param object the value.
     */
    abstract T increase(final T object, int step);
}
