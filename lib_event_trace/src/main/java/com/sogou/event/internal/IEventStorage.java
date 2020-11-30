package com.sogou.event.internal;

/**
 * Storage.
 *
 * @author lvchong
 * @date 19-12-10
 */
public interface IEventStorage {

    int DEFAULT_INT = 0;
    long DEFAULT_LONG = 0;
    float DEFAULT_FLOAT = 0;
    String DEFAULT_STRING = "";

    public void putInt(String key, int value);

    public int getInt(String key);

    public int getInt(String key, int defaultValue);

    public void putLong(String key, long value);

    public long getLong(String key);

    public long getLong(String key, long defaultValue);

    public void putFloat(String key, float value);

    public float getFloat(String key);

    public float getFloat(String key, float defaultValue);

    public void putString(String key, String value);

    public String getString(String key);

    public String getString(String key, String defaultValue);

    public void remove(String key);

    public void cleanAll();
}
