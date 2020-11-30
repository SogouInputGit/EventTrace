package com.sogou.event.internal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The param proto for the event.
 *
 * @author lvchong
 */
public class EventParamProto {

    @SerializedName("argInt0")
    @Expose
    private int argInt0;
    @SerializedName("argInt1")
    @Expose
    private int argInt1;
    @SerializedName("argInt2")
    @Expose
    private int argInt2;

    @SerializedName("argString0")
    @Expose
    private String argString0;
    @SerializedName("argString1")
    @Expose
    private String argString1;
    @SerializedName("argString2")
    @Expose
    private String argString2;

    @SerializedName("argString3")
    @Expose
    private String argString3;

    @SerializedName("argArray")
    @Expose
    private String[] argArray;

    @SerializedName("argIntArray")
    @Expose
    private Integer[] argIntArray;

    private volatile boolean inUse;
    private EventParamProto next;
    private static final Object sPoolSync = new Object();
    private static EventParamProto sPool;
    private static int sPoolSize = 0;
    private static final int MAX_POOL_SIZE = 24;

    private EventParamProto() {

    }

    public static EventParamProto obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                EventParamProto e = sPool;
                e.setInUse(false);

                sPool = e.next;
                e.next = null;
                sPoolSize--;
                return e;
            }
        }
        return new EventParamProto();
    }

    public void recycle() {
        if (isInUse()) {
            Preconditions.throwCondition("Already In used.");
            return;
        }
        doRecycle();
    }

    private void checkIsInPool() {
        if (isInUse()) {
            throw new RuntimeException("Use EventProto when it's in pool");
        }
    }

    private void doRecycle() {
        setInUse(true);

        setArgString0(null);
        setArgString1(null);
        setArgArray(null);
        setArgString2(null);
        setArgString3(null);

        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }


    public int getArgInt0() {
        checkIsInPool();
        return argInt0;
    }

    public void setArgInt0(int argInt0) {
        this.argInt0 = argInt0;
    }

    public int getArgInt1() {
        checkIsInPool();
        return argInt1;
    }

    public void setArgInt1(int argInt1) {
        this.argInt1 = argInt1;
    }

    public int getArgInt2() {
        checkIsInPool();
        return argInt2;
    }

    public void setArgInt2(int argInt2) {
        this.argInt2 = argInt2;
    }

    public String getArgString0() {
        checkIsInPool();
        return argString0;
    }

    public void setArgString0(String argString0) {
        this.argString0 = argString0;
    }

    public String getArgString1() {
        checkIsInPool();
        return argString1;
    }

    public void setArgString1(String argString1) {
        this.argString1 = argString1;
    }

    public String getArgString2() {
        checkIsInPool();
        return argString2;
    }

    public void setArgString2(String argString2) {
        this.argString2 = argString2;
    }

    public String getArgString3() {
        checkIsInPool();
        return argString3;
    }

    public void setArgString3(String argString3) {
        this.argString3 = argString3;
    }

    public String[] getArgArray() {
        checkIsInPool();
        return argArray;
    }

    public void setArgArray(String[] argArray) {
        this.argArray = argArray;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public Integer[] getArgIntArray() {
        checkIsInPool();
        return argIntArray;
    }

    public void setArgIntArray(Integer[] argIntArray) {
        this.argIntArray = argIntArray;
    }
}
