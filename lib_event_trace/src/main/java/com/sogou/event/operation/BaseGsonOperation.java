package com.sogou.event.operation;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author dongjianye on 2020/6/5
 *
 */
public abstract class BaseGsonOperation<T> implements IGsonOperation<T>, IOperation<T, String>{

    @Override
    public final String serialize(Gson gson, Type type, T memory) {
        try {
            return gson.toJson(memory, type);
        } catch (Throwable e) {
            return null;
        }
    }

    @Override
    public final T deserialize(Gson gson, Type type, String storage) {
        try {
            return gson.fromJson(storage, type);
        } catch (Throwable e) {
            return null;
        }
    }


}
