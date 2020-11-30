package com.sohu.inputmethod.foreign.base.thread;

/**
 * @author jiany on 2020/4/10
 *
 * @param <V> the method argument {@code call}
 */
@FunctionalInterface
public interface Callback<V> {
    /**
     * Use the result, or throws an exception if unable to do so.
     *
     * @throws Exception if unable to use the result
     */
    void callback(V result) throws Exception;
}