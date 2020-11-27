package com.sohu.inputmethod.foreign.pingback.eventtrace.evaluation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author feixuezheng
 * 上报ic内容差异的数据结构
 */
public class DiffIcCompareBean {
    public String getExpected() {
        return mExpected;
    }

    public void setExpected(String expected) {
        mExpected = expected;
    }

    public String getActual() {
        return mActual;
    }

    public void setActual(String actual) {
        mActual = actual;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getCursor() {
        return mCursor;
    }

    public void setCursor(String cursor) {
        mCursor = cursor;
    }

    @SerializedName("exp")
    @Expose
    private String mExpected;

    @SerializedName("act")
    @Expose
    private String mActual;

    @SerializedName("pkg")
    @Expose
    private String mPackageName;

    @SerializedName("cursor")
    @Expose
    private String mCursor;
}
