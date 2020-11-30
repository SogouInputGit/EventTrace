package com.sogou.event.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * Keep the record in memory.
 *
 * @author lvchong
 */
public class MemoryCache {
    private static final String TAG = "RecordMemoryCache";

    private final Map<String, Object> mCache;

    public MemoryCache() {
        mCache = new HashMap<>();
    }

    public Object get(String key){
        return mCache.get(key);
    }

    public void put(String key, Object value){
        mCache.put(key, value);
    }

    public void reset() {
        mCache.clear();
    }

    public void remove(String key) {
        mCache.remove(key);
    }

    public void clear() {
        mCache.clear();
    }
}
