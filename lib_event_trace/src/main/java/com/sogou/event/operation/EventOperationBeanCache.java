package com.sogou.event.operation;

import android.support.v4.util.ArrayMap;
import android.util.Pair;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dongjianye on 2020/6/8
 * 需要自动注册一些类型，供框架用，但是由于pingback的大量类型是相同的，所以需要做些类型上缓存的优化
 * 用户在定义的时候，用的是 <String, IOperation.class>，框架需要的是<String, EventOperationBean>
 * 因此，需要根据一个IOperation.class生成一个EventOperationBean，这个是框架自动生成的
 * 一个IOperation.class对应一个EventOperationBean，这个前提要保证
 *
 * 混存类型种类有多个纬度
 * TYPE：比如说OneMapOperation，以及自定义的CustomGsonOperation，都可能用同一个HashMap<String, String>
 *      TYPE是一致的，因此内存里面只需保存一个
 * OPERATION: 多个
 */
class EventOperationBeanCache {

    private final static ReentrantLock TYPE_LOCK = new ReentrantLock();

    // 只有初始化的时候才用到，初始化之后全部清空
    // 针对Type，Type对应的是BeanType, 这里面的class对应的是实际数据类型
    private final static Map<Type, Pair<Type, Class>> TYPE_CLASS_HASH_MAP = new ArrayMap<>();
    // 针对整个GsonRecord，还有一个缓存
    private final static Map<Class, EventOperationBean> EVENT_OPERATION_BEAN_CACHE = new ArrayMap<>();

    // EventRecord改成多实例后，确实在不同的实例执行到init的时候，存在竞争的情况，这里去掉这个限制。（出现崩溃是因为这个if else写错了）
    // 因此也不能执行清空的操作了，
    private static void acquireLock() {
        TYPE_LOCK.lock();
    }

    private static void releaseLock() {
        TYPE_LOCK.unlock();
    }

    static EventOperationBean getEventOperationBeanFromOperationClass(final Class<? extends IOperation> clazz,
                                                                      final OperationInstanceCreator<? extends IOperation> creator) {
        acquireLock();

        try {
            EventOperationBean operationBean = EVENT_OPERATION_BEAN_CACHE.get(clazz);
            if (operationBean != null) {
                return operationBean;
            }

            final Type type = clazz.getGenericSuperclass();
            // 对BaseMomentaryOperation做个特殊处理
            if (type == BaseMomentaryOperation.class) {
                IOperation operation;
                try {
                    operation = (IOperation) clazz.newInstance();
                } catch (Throwable ignored) {
                    throw new RuntimeException("IOperation new instance failed: " + clazz);
                }
                operationBean = new EventOperationBean(null, null, operation);
                EVENT_OPERATION_BEAN_CACHE.put(clazz, operationBean);
                return operationBean;
            }

            if (!(type instanceof ParameterizedType)) {
                throw new RuntimeException("IOperation must be parameterized type: " + clazz);
            }

            final Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypes == null || actualTypes.length != 1) {
                throw new RuntimeException("IOperation must have 1 parameterized type: " + clazz);
            }

            Pair<Type, Class> typeClassPair = getTypeClassFromCache(actualTypes[0]);

            // 生成一个Operation的实例，这里直接反射了
            IOperation operation;
            try {
                if (creator == null) {
                    operation = (IOperation) clazz.newInstance();
                } else {
                    operation = creator.createInstance(clazz);
                }
            } catch (Throwable ignored) {
                throw new RuntimeException("IOperation new instance failed: " + clazz);
            }

            operationBean = new EventOperationBean(typeClassPair.first, typeClassPair.second, operation);
            EVENT_OPERATION_BEAN_CACHE.put(clazz, operationBean);

            return operationBean;



        } finally {
            releaseLock();
        }
    }

    private static Pair<Type, Class> getTypeClassFromCache(Type type) {
        acquireLock();

        try {
            Pair<Type, Class> beanTypes = TYPE_CLASS_HASH_MAP.get(type);
            if (beanTypes == null) {
                final TypeToken typeToken = TypeToken.get(type);
                beanTypes = new Pair<>(typeToken.getType(), typeToken.getRawType());
                TYPE_CLASS_HASH_MAP.put(type, beanTypes);
            }

            return beanTypes;
        } finally {
            releaseLock();
        }
    }
}
