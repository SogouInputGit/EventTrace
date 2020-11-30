package com.sogou.event.operation;

import com.sogou.event.internal.IEventStorage;
import com.sogou.event.internal.EventProto;

/**
 * @author dongjianye on 2020/6/5
 * T 代表内存中数据类型，V 代表存储在文件中的数据类型
 */
public interface IOperation<T, V> {

    public T operate(T object, EventProto event);

    public V getFromStorage(final String key, final IEventStorage storage);

    public void setToStorage(final String key, final V value, final IEventStorage storage);

    public boolean useMemoryCache();

    public String get(final String key, final IEventStorage storage);

    public void reset(final String key, final IEventStorage storage);
}
