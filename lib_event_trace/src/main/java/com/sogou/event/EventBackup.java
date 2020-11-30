package com.sogou.event;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.GuardedBy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.sogou.event.internal.EventParamProto;
import com.sogou.event.internal.EventProto;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongjianye on 2020/6/5
 *
 * 在发送过程中，需要先将命令保存起来，发送完成后，再将这些命令全部交给Engine来处理
 *
 */
class EventBackup {
    private static final String TAG = "EventBackup";

    private static final String BACKUP_FILE_NAME = "foreign_event_backup";

    @GuardedBy("mMonitor.mStorageLock")
    private final List<EventProto> mEventList;

    private Gson mGson;

    private File mBackupFile;
    private RandomAccessFile mRandomAccessFile;

    private String mLineSeparator;

    private final Monitor mMonitor;

    private final String mId;

    private final String mBackupDir;

    // backupDir为空，表示不使用文件存储，只保存在内存当中
    EventBackup(Monitor monitor, String id, String backupDir) {
        mEventList = new ArrayList<>();

        mMonitor = monitor;

        mId = id;

        mBackupDir = backupDir;
    }

    private void initGson() {
        if (mGson == null) {
            mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(EventProto.class, new EventProtoInstanceCreator())
                    .registerTypeAdapter(EventParamProto.class, new EventParamProtoInstanceCreator())
                    .create();
        }
    }

    @GuardedBy("Monitor.sStorageLock")
    private void checkOrCreateFile() {
        if (TextUtils.isEmpty(mBackupDir) || mBackupFile != null) {
            return;
        }

        try {
            final File dir = new File(mBackupDir);
            if (!dir.exists() && !dir.mkdirs()) {
                mMonitor.recordError(Monitor.ERROR_CREATE_BACKUP_DIR);
                return;
            }

            mBackupFile = new File(mBackupDir, BACKUP_FILE_NAME + mId);
            if (!mBackupFile.exists() && !mBackupFile.createNewFile()) {
                mBackupFile = null;
                mMonitor.recordError(Monitor.ERROR_CREATE_BACKUP_FILE);
                return;
            }

            mRandomAccessFile = new RandomAccessFile(mBackupFile, "rw");

        } catch (Throwable ignored) {
            mBackupFile = null;
            mRandomAccessFile = null;
        }
    }

    @GuardedBy("Monitor.sStorageLock")
    List<EventProto> getBackupEventFromFile() {
        synchronized (mMonitor.mStorageLock) {
            checkOrCreateFile();

            if (mRandomAccessFile == null) {
                return null;
            }

            try {
                long length = mRandomAccessFile.length();
                if (length <= 0) {
                    return null;
                }
            } catch (IOException ignored) {
                return null;
            }

            final List<EventProto> result = new ArrayList<>();
            try {
                mRandomAccessFile.seek(0);
                String line;
                while ((line = mRandomAccessFile.readLine()) != null) {
                    EventProto eventProto = null;
                    try {
                        initGson();
                        eventProto = mGson.fromJson(line, EventProto.class);
                    } catch (Throwable e) {
                    }

                    if (eventProto != null) {
                        if (BuildConfig.DEBUG) {
                            Log.d(Monitor.TAG, "getBackupEventFromFile : " + line);
                        }
                        result.add(eventProto);
                    }
                }
                mRandomAccessFile.setLength(0);
            } catch (IOException ignored) {
            }

            return result;
        }
    }

    private String getLineSeparator() {
        if (!TextUtils.isEmpty(mLineSeparator)) {
            return mLineSeparator;
        } else {
            mLineSeparator = System.getProperty("line.separator");
            if (TextUtils.isEmpty(mLineSeparator)) {
                mLineSeparator = "\r\n";
            }
        }
        return mLineSeparator;
    }

    @GuardedBy("Monitor.sStorageLock")
    void addToBackup(EventProto event) {
        mEventList.add(event);
        checkOrCreateFile();
        appendEventToFile(event);
    }

    @GuardedBy("Monitor.sStorageLock")
    void clearBackupFile() {
        if (mRandomAccessFile == null) {
            return;
        }

        try {
            mRandomAccessFile.seek(0);
            mRandomAccessFile.setLength(0);
        } catch (IOException e) {

        }
    }

    @GuardedBy("Monitor.sStorageLock")
    List<EventProto> getAndClearBackup() {
        if (!mEventList.isEmpty()) {
            clearBackupFile();
        }
        final List<EventProto> result = new ArrayList<>(mEventList);
        mEventList.clear();
        return result;
    }

    @GuardedBy("mMonitor.mStorageLock")
    private void appendEventToFile(final EventProto event) {
        if (event == null || mRandomAccessFile == null) {
            return;
        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "add to backup : " + event.getKey() + " " + event);
        }

        final String newLine;
        try {
            initGson();
            newLine = mGson.toJson(event);
        } catch (Throwable ignored) {
            mMonitor.recordError(Monitor.ERROR_SERIALIZE_TO_BACKUP);
            return;
        }

        if (!TextUtils.isEmpty(newLine)) {
            try {
                mRandomAccessFile.writeBytes(newLine);
                mRandomAccessFile.writeBytes(getLineSeparator());

                if (BuildConfig.DEBUG) {
                    Log.d(Monitor.TAG, "appendEventToFile : " + newLine);
                }

            } catch (IOException ignored) {
                mMonitor.recordError(Monitor.ERROR_WRITE_BACKUP_FILE);
            }
        }
    }

    private static class EventParamProtoInstanceCreator implements InstanceCreator<EventParamProto> {

        @Override
        public EventParamProto createInstance(Type type) {
            return EventParamProto.obtain();
        }
    }

    private static class EventProtoInstanceCreator implements InstanceCreator<EventProto> {

        @Override
        public EventProto createInstance(Type type) {
            return EventProto.obtain();
        }
    }
}
