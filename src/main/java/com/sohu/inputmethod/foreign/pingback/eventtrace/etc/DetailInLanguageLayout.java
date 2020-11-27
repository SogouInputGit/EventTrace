package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * For Ec38
 *
 * @author lvchong
 * @date 19-12-10
 */
@Keep
public class DetailInLanguageLayout {


    @SerializedName("lan")
    @Expose
    private int lan;

    @SerializedName("clickCount")
    @Expose
    private int clickCount;

    @SerializedName("settingCount")
    @Expose
    private int settingCount;

    @SerializedName("settingsLog")
    @Expose
    private ArrayList<Integer> settingsLog;

    public int getLan() {
        return lan;
    }

    public void setLan(int lan) {
        this.lan = lan;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getSettingCount() {
        return settingCount;
    }

    public void setSettingCount(int settingCount) {
        this.settingCount = settingCount;
    }

    public ArrayList<Integer> getSettingsLog() {
        return settingsLog;
    }

    public void setSettingsLog(ArrayList<Integer> settingsLog) {
        this.settingsLog = settingsLog;
    }
}
