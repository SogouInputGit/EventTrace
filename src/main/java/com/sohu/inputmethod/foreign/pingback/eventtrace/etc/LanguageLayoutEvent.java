package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
/**
 * @author lvchong
 */
public class LanguageLayoutEvent {
    @SerializedName("i")
    @Expose
    public int i;

    @SerializedName("l")
    @Expose
    public int l;

    @SerializedName("n")
    @Expose
    public int n;
}
