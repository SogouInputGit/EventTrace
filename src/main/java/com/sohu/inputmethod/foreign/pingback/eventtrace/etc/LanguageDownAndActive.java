package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * For Ec36
 *
 * @author lvchong
 * @date 19-12-10
 */
@Keep
public class LanguageDownAndActive {

    @SerializedName("src")
    @Expose
    private String src;

    @SerializedName("lans")
    @Expose
    private ArrayList<Integer> lans;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public ArrayList<Integer> getLans() {
        return lans;
    }

    public void setLans(ArrayList<Integer> lans) {
        this.lans = lans;
    }

}
