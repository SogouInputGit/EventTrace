package com.sohu.inputmethod.foreign.pingback.eventtrace.evaluation;

import android.support.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * For Ec36
 *
 * @author lvchong
 * @date 19-12-10
 */
@Keep
public class IcEvaluationBean {

    @SerializedName("tt")
    @Expose
    private long icInvokeTimes;

    @SerializedName("tbct")
    @Expose
    private long tbcInvokeTimes;

    @SerializedName("tact")
    @Expose
    private long tacInvokeTimes;

    @SerializedName("selt")
    @Expose
    private long selectTextInvokeTimes;

    @SerializedName("extt")
    @Expose
    private long extractedTextInvokeTimes;

    @SerializedName("hittac")
    @Expose
    private long hitTacCacheTimes;

    @SerializedName("hittbc")
    @Expose
    private long hitTbcCacheTimes;

    @SerializedName("hitsel")
    @Expose
    private long hitSelCacheTimes;

    @SerializedName("hitext")
    @Expose
    private long hitExtCacheTimes;

    @SerializedName("ib")
    @Expose
    private long initBufferInvokeTimes;

    @SerializedName("ibf")
    @Expose
    private long initBufferWhenStartInvokeTimes;

    @SerializedName("rest")
    @Expose
    private long resetTimes;

    @SerializedName("resft")
    @Expose
    private long resetDueToFinishInvokeTimes;

    @SerializedName("diftac")
    @Expose
    private long diffTacTimes;

    @SerializedName("diftaclist")
    @Expose
    private List<DiffIcCompareBean> mDiffTac;

    @SerializedName("diftbc")
    @Expose
    private long diffTbcTimes;

    @SerializedName("diftbclist")
    @Expose
    private List<DiffIcCompareBean> mDiffTbc;

    @SerializedName("difsel")
    @Expose
    private long diffSelectedTextTimes;

    @SerializedName("difsellist")
    @Expose
    private List<DiffIcCompareBean> mDiffSel;

    @SerializedName("difext")
    @Expose
    private long diffExtractedTextTimes;

    @SerializedName("difextlist")
    @Expose
    private List<DiffIcCompareBean> mDiffExt;

    public long getIcInvokeTimes() {
        return icInvokeTimes;
    }

    public void setIcInvokeTimes(long icInvokeTimes) {
        this.icInvokeTimes = icInvokeTimes;
    }

    public long getTbcInvokeTimes() {
        return tbcInvokeTimes;
    }

    public void setTbcInvokeTimes(long tbcInvokeTimes) {
        this.tbcInvokeTimes = tbcInvokeTimes;
    }

    public long getTacInvokeTimes() {
        return tacInvokeTimes;
    }

    public void setTacInvokeTimes(long tacInvokeTimes) {
        this.tacInvokeTimes = tacInvokeTimes;
    }

    public long getSelectTextInvokeTimes() {
        return selectTextInvokeTimes;
    }

    public void setSelectTextInvokeTimes(long selectTextInvokeTimes) {
        this.selectTextInvokeTimes = selectTextInvokeTimes;
    }

    public long getExtractedTextInvokeTimes() {
        return extractedTextInvokeTimes;
    }

    public void setExtractedTextInvokeTimes(long extractedTextInvokeTimes) {
        this.extractedTextInvokeTimes = extractedTextInvokeTimes;
    }

    public long getHitTacCacheTimes() {
        return hitTacCacheTimes;
    }

    public void setHitTacCacheTimes(long hitTacCacheTimes) {
        this.hitTacCacheTimes = hitTacCacheTimes;
    }

    public long getHitTbcCacheTimes() {
        return hitTbcCacheTimes;
    }

    public void setHitTbcCacheTimes(long hitTbcCacheTimes) {
        this.hitTbcCacheTimes = hitTbcCacheTimes;
    }

    public long getHitSelCacheTimes() {
        return hitSelCacheTimes;
    }

    public void setHitSelCacheTimes(long hitSelCacheTimes) {
        this.hitSelCacheTimes = hitSelCacheTimes;
    }

    public long getHitExtCacheTimes() {
        return hitExtCacheTimes;
    }

    public void setHitExtCacheTimes(long hitExtCacheTimes) {
        this.hitExtCacheTimes = hitExtCacheTimes;
    }

    public long getInitBufferInvokeTimes() {
        return initBufferInvokeTimes;
    }

    public void setInitBufferInvokeTimes(long initBufferInvokeTimes) {
        this.initBufferInvokeTimes = initBufferInvokeTimes;
    }

    public long getInitBufferWhenStartInvokeTimes() {
        return initBufferWhenStartInvokeTimes;
    }

    public void setInitBufferWhenStartInvokeTimes(long initBufferWhenStartInvokeTimes) {
        this.initBufferWhenStartInvokeTimes = initBufferWhenStartInvokeTimes;
    }

    public long getResetTimes() {
        return resetTimes;
    }

    public void setResetTimes(long resetTimes) {
        this.resetTimes = resetTimes;
    }

    public long getResetDueToFinishInvokeTimes() {
        return resetDueToFinishInvokeTimes;
    }

    public void setResetDueToFinishInvokeTimes(long resetDueToFinishInvokeTimes) {
        this.resetDueToFinishInvokeTimes = resetDueToFinishInvokeTimes;
    }

    public long getDiffTacTimes() {
        return diffTacTimes;
    }

    public void setDiffTacTimes(long diffTacTimes) {
        this.diffTacTimes = diffTacTimes;
    }

    public List<DiffIcCompareBean> getDiffTac() {
        return mDiffTac;
    }

    public void setDiffTac(List<DiffIcCompareBean> diffTac) {
        mDiffTac = diffTac;
    }

    public long getDiffTbcTimes() {
        return diffTbcTimes;
    }

    public void setDiffTbcTimes(long diffTbcTimes) {
        this.diffTbcTimes = diffTbcTimes;
    }

    public List<DiffIcCompareBean> getDiffTbc() {
        return mDiffTbc;
    }

    public void setDiffTbc(List<DiffIcCompareBean> diffTbc) {
        mDiffTbc = diffTbc;
    }

    public long getDiffSelectedTextTimes() {
        return diffSelectedTextTimes;
    }

    public void setDiffSelectedTextTimes(long diffSelectedTextTimes) {
        this.diffSelectedTextTimes = diffSelectedTextTimes;
    }

    public List<DiffIcCompareBean> getDiffSel() {
        return mDiffSel;
    }

    public void setDiffSel(List<DiffIcCompareBean> diffSel) {
        mDiffSel = diffSel;
    }

    public long getDiffExtractedTextTimes() {
        return diffExtractedTextTimes;
    }

    public void setDiffExtractedTextTimes(long diffExtractedTextTimes) {
        this.diffExtractedTextTimes = diffExtractedTextTimes;
    }

    public List<DiffIcCompareBean> getDiffExt() {
        return mDiffExt;
    }

    public void setDiffExt(List<DiffIcCompareBean> diffExt) {
        mDiffExt = diffExt;
    }
}
