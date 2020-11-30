package com.sogou.event;

import com.sogou.event.internal.EventProto;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;

/**
 * @author dongjianye on 2020/6/5
 *
 * 命令收集和分发，收到后立马直接交给引擎，引擎处理每个请求
 */
final class EventDispatcher extends Thread {

    private final BlockingQueue<EventProto> mQueue;
    private final EventEngine mEngine;
    private final EventBackup mBackup;
    private final AtomicBoolean mBackupRead = new AtomicBoolean(false);
    private final Monitor mMonitor;

    EventDispatcher(EventEngine engine, EventBackup backup, Monitor monitor) {
        super("TypanyEventCollector_" + monitor.getId());
        mEngine = engine;
        mQueue = new LinkedBlockingDeque<>();
        mBackup = backup;
        mMonitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            // 先把保存在备份文件里面的取出来扔到队列里面，这里暂时不考虑从文件读取的时候，正好有人往队列塞数据导致的不一致的问题
            if (mBackupRead.compareAndSet(false, true)) {

                final List<EventProto> backup = mBackup.getBackupEventFromFile();
                if (backup != null) {
                    for (EventProto eventProto : backup) {
                        trackEvent(eventProto);
                        if (BuildConfig.DEBUG) {
                            mMonitor.addSuspending();
                        }
                    }
                }
                continue;
            }

            EventProto event;
            try {
                event = mQueue.take();
            } catch (InterruptedException e) {
                event = null;
            }

            if (event == null) {
                continue;
            }

            mEngine.process(event);
        }
    }

    public void trackEvent(@NonNull EventProto event) {
        try {
            mQueue.put(event);
        } catch (InterruptedException e) {
        }
    }
}
