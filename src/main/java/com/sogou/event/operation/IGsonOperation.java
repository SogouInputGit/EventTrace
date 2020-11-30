package com.sogou.event.operation;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author dongjianye on 2020/6/8
 */
public interface IGsonOperation<T> {

    public String serialize(Gson gson, Type type, T memory);

    public T deserialize(Gson gson, Type type, String storage);
}
