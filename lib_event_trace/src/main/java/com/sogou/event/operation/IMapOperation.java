package com.sogou.event.operation;

/**
 * @author dongjianye on 2020/6/5
 * 标准的map结构的支持
 */
interface IMapOperation<T> {

    /**
     * Increase the record of the item in the key.
     *
     * @param object the value.
     * @param item
     */
    T increase(final T object, String item, int step);

    /**
     * Increase the record of the item in the key.
     *
     * @param object   the record's name.
     * @param paths The paths to item in the key.
     * @param step The step.
     */
    T increase(final T object, String[] paths, int step);
}
