package com.sogou.event.operation;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.sogou.event.BuildConfig;
import com.sogou.event.internal.Preconditions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author dongjianye
 */
public class EventDefineFactory {

    // 为了让EventRecord支持多实例，才有的这个，互相不干扰吧
    private final static ArrayMap<String, EventDefineFactory> sEventDefines = new ArrayMap<>();

    private static EventDefineFactory getInstance(String id) {
        synchronized (sEventDefines) {
            EventDefineFactory eventDefineFactory = sEventDefines.get(id);
            if (eventDefineFactory == null) {
                eventDefineFactory = new EventDefineFactory(id);
                sEventDefines.put(id, eventDefineFactory);
            }
            return eventDefineFactory;
        }
    }

    private final String mId;

    private EventDefineFactory(String id) {
        mId = id;
    }

    // 保证按照key的大小来排序，比较好查看
    private final Map<String, EventOperationBean> mRecordDefines = new TreeMap<>();
    // 临时变量，以后初始化之后，就不会再使用了
    private final Set<BaseEventDefine> mDefineRegisters = new HashSet<>();
    private final Map<Class<? extends IOperation>, OperationInstanceCreator<? extends IOperation>> instanceCreators
            = new HashMap<>();

    private volatile boolean mInited = false;

    private void init() {
        if (!mInited) {
            synchronized (mRecordDefines) {
                if (!mInited) {
                    final long curTime = System.currentTimeMillis();
                    addAllOperationCreatorByAop(mId);
                    addAllDefinesByAop(mId);
                    defineAllRecordUnlocked();
                    mDefineRegisters.clear();
                    instanceCreators.clear();
                    if (BuildConfig.DEBUG) {
                        Log.d("EventDefineFactory", "EventDefineFactory init cost " + (System.currentTimeMillis() - curTime) + " ms");
                    }
                    mInited = true;
                }
            }
        }
    }

    public static void registerOperationAdapter(String id, Class clazz, OperationInstanceCreator<? extends IOperation> creator) {
        EventDefineFactory defineFactory = getInstance(id);
        defineFactory.registerOperationAdapter(clazz, creator);
    }

    public static void registerEventDefine(String id, BaseEventDefine eventDefine) {
        EventDefineFactory defineFactory = getInstance(id);
        defineFactory.registerEventDefine(eventDefine);
    }

    private void registerOperationAdapter(Class clazz, OperationInstanceCreator<? extends IOperation> creator) {
        if (mInited) {
            throw new RuntimeException("must register operation adapter before init or not work");
        }
        if (BuildConfig.DEBUG && instanceCreators.containsKey(clazz)) {
            Preconditions.throwCondition("repeat register operation adapter on " + clazz);
        }
        instanceCreators.put(clazz, creator);
    }

    private void registerEventDefine(BaseEventDefine eventDefine) {
        if (mInited) {
            throw new RuntimeException("must register event define before init or not work");
        }
        mDefineRegisters.add(eventDefine);
    }

    private void defineAllRecordUnlocked() {
        for (BaseEventDefine define : mDefineRegisters) {
            if (define == null) {
                continue;
            }
            defineJsonRecordUnlocked(define);
        }

        mDefineRegisters.clear();
    }

    // TODO 注解要修改的函数
    private void addAllOperationCreatorByAop(String id) {

    }

    // TODO 注解要修改的函数
    private void addAllDefinesByAop(String id) {
        // 注解生成如下代码
//        mDefineRegisters.add(new TestDefine());
    }


    private void defineJsonRecordUnlocked(BaseEventDefine define) {
        if (define == null) {
            return;
        }

        define.initDefines();
        final Map<String, Class<? extends IOperation>> recordMap = define.getRecordDefines();
        if (recordMap == null) {
            return;
        }

        for (Map.Entry<String, Class<? extends IOperation>> entry : recordMap.entrySet()) {
            final EventOperationBean operationBean = EventOperationBeanCache
                    .getEventOperationBeanFromOperationClass(entry.getValue(), instanceCreators.get(entry.getValue()));
            mRecordDefines.put(entry.getKey(), operationBean);
        }

        define.clear();
    }

    // BaseMomentaryOperation 瞬时值比较特殊，不走框架，不能走到这个函数里面
    public static @Nullable EventOperationBean getCommandRecord(String id, String key) {
        EventDefineFactory defineFactory = getInstance(id);
        defineFactory.init();
        final EventOperationBean record = defineFactory.mRecordDefines.get(key);
        if (record == null) {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("Unsuportted record type : " + key);
            }
        }

        return record;
    }


    public static Set<String> getKeys(String id) {
        EventDefineFactory defineFactory = getInstance(id);
        defineFactory.init();
        return defineFactory.mRecordDefines.keySet();
    }

}
