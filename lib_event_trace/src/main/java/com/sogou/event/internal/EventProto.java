package com.sogou.event.internal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Proto for the event.
 *
 * @author lvchong
 */
public class EventProto {

    // 注意：这里面的值不能修改，因为备份数据里面已经有定义了，如果改了会出问题，
    // 因为老代码定义太随意，没办法了，只能换个存储来存了
    public static final int EVENT_TYPE_NULL = -1;

    // Int or long operation
    public static final int EVENT_TYPE_INCREASE_KEY_STEP = 0;   // 给NumberOperation用的

    // map operation
    public static final int EVENT_TYPE_INCREASE_KEY_ITEM_STEP = 101; // 给BaseMapOperation用的
    public static final int EVENT_TYPE_INCREASE_KEY_PATHS_STEP= 102; // 给BaseMapOperation用的

    // 如果需要修改自定义的操作，就在这中间添加

    public static final int EVENT_TYPE_ACTION = 10000; // 没办法了，理论上这个应该设置个很大的值

    @SerializedName("eventType")
    @Expose
    private int eventType;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("actionType")
    @Expose
    private int actionType;

    @SerializedName("step")
    @Expose
    private int step;

    @SerializedName("item")
    @Expose
    private String item;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("paths")
    @Expose
    private String[] paths;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("param")
    @Expose
    private EventParamProto param;

    private volatile boolean inPool;
    private EventProto next;
    private static final Object sPoolSync = new Object();
    private static EventProto sPool;
    private static int sPoolSize = 0;
    private static final int MAX_POOL_SIZE = 24;

    private EventProto() {

    }

    public static EventProto obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                EventProto e = sPool;
                e.setInPool(false);

                sPool = e.next;
                e.next = null;
                sPoolSize--;

                return e;
            }
        }
        return new EventProto();
    }

    public void recycle() {
        if (isInPool()){
            Preconditions.throwCondition("Already In Pool.");
            return;
        }
        doRecycle();
    }

    private void checkIsInPool() {
        if (isInPool()) {
            throw new RuntimeException("Use EventProto when it's in pool");
        }
    }

    private void doRecycle() {
        setInPool(true);

        if (param != null) {
            param.recycle();
            setParam(null);
        }

        setEventType(EVENT_TYPE_NULL);
        setKey(null);
        setItem(null);
        setPath(null);
        setValue(null);

        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    public int getEventType() {
        checkIsInPool();
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getKey() {
        checkIsInPool();
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStep() {
        checkIsInPool();
        return (int) step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getItem() {
        checkIsInPool();
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPath() {
        checkIsInPool();
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getPaths() {
        checkIsInPool();
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public String getValue() {
        checkIsInPool();
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getActionType() {
        checkIsInPool();
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public EventParamProto getParam() {
        checkIsInPool();
        return param;
    }

    public void setParam(EventParamProto param) {
        this.param = param;
    }

    public boolean isInPool() {
        return inPool;
    }

    public void setInPool(boolean inPool) {
        this.inPool = inPool;
    }
}
