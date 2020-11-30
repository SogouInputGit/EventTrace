package com.sogou.event.storage;

import android.content.Context;

import com.sogou.event.internal.IEventStorage;
import com.tencent.mmkv.MMKV;

/**
 * The storage of MMKV.
 *
 * @author lvchong
 * @date 19-12-10
 */
public class MmkvStorage implements IEventStorage {
    private static final String TAG = "MmkvStorage";

    private static String MMKV_FILE_PREFIX = "foreign_mmkv_db_";

    private volatile MMKV mMmkv;
    private final int mMultiMode;
    private final String mId;
    private final Object mLock = new Object();
    private final Context mApplicationContext;

    public MmkvStorage(Context applicationContext, String id, int multiMode) {
        mId = id;
        mMultiMode = multiMode;

        mApplicationContext = applicationContext;
    }

    // 不确定mmkv是new的时候会不会产生崩溃，这个加个catch
    private MMKV checkOrCreateMmmkv() {
        assetMmkv(mApplicationContext);
        if (mMmkv == null) {
            synchronized (mLock) {
                if (mMmkv == null) {
                    // 加catch也不行了，崩在内核了
                    if (mId != null) {
                        mMmkv = MMKV.mmkvWithID(MMKV_FILE_PREFIX + mId, mMultiMode);
                    } else {
                        mMmkv = MMKV.defaultMMKV();
                    }
                }
            }
        }
        return mMmkv;
    }

    private static void assetMmkv(Context applicationContext) {
        if (MMKV.getRootDir() == null) {
            MMKV.initialize(applicationContext);
        }
    }

    public void putInt(String key, int value) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            mmkv.putInt(key, value);
        }
    }

    @Override
    public int getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            return mmkv.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void putLong(String key, long value) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            mmkv.putLong(key, value);
        }
    }

    @Override
    public long getLong(String key) {
        return getLong(key, DEFAULT_LONG);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            return mmkv.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void putFloat(String key, float value) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            mmkv.putFloat(key, value);
        }
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, DEFAULT_FLOAT);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            return mmkv.getFloat(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void putString(String key, String value) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            mmkv.putString(key, value);
        }
    }

    @Override
    public String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    public String getString(String key, String defaultValue) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            return mmkv.getString(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void remove(String key) {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            mmkv.remove(key);
        }
    }

    @Override
    public void cleanAll() {
        final MMKV mmkv = checkOrCreateMmmkv();
        if (mmkv != null) {
            mmkv.clearAll();
        }
    }
}
