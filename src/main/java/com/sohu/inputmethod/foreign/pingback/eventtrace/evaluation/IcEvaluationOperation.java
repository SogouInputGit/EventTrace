package com.sohu.inputmethod.foreign.pingback.eventtrace.evaluation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sogou.event.BuildConfig;
import com.sogou.event.internal.EventProto;
import com.sogou.event.operation.CustomGsonOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for ec36
 *
 * @author lvchong
 */
public class IcEvaluationOperation extends CustomGsonOperation<IcEvaluationBean> {

    public static final int ACTION_IC_INVOKE = 1;
    public static final int ACTION_GET_TAC_INVOKE = 2;
    public static final int ACTION_GET_TBC_INVOKE = 3;
    public static final int ACTION_GET_SEL_INVOKE = 4;
    public static final int ACTION_GET_EXT_INVOKE = 5;

    public static final int ACTION_HIT_TAC_CACHE = 6;
    public static final int ACTION_HIT_TBC_CACHE = 7;
    public static final int ACTION_HIT_SEL_CACHE = 8;
    public static final int ACTION_HIT_EXT_CACHE = 9;

    public static final int ACTION_RESET_INVOKE = 10;
    public static final int ACTION_ON_FINISH_RESET_INVOKE = 11;
    public static final int ACTION_INIT_BUFFER_INVOKE = 12;
    public static final int ACTION_INIT_BUFFER_ON_START_INVOKE = 13;

    public static final int ACTION_DIFF_GET_TAC_INVOKE = 14;
    public static final int ACTION_DIFF_GET_TBC_INVOKE = 15;
    public static final int ACTION_DIFF_GET_SEL_INVOKE = 16;
    public static final int ACTION_DIFF_GET_EXT_INVOKE = 17;

    private static final int MAX_RECORD_DIFF_ITEMS_COUNT = 20;
    private static final int IC_INVOKE_TIMES_GROUP = 20;
    private long mIcInvokeRealTimes;
    @Override
    public IcEvaluationBean action(IcEvaluationBean object, @NonNull EventProto event) {
        if (object == null) {
            object = new IcEvaluationBean();
        }
        switch (event.getActionType()){
            case ACTION_IC_INVOKE:
                mIcInvokeRealTimes += 1;
                if(mIcInvokeRealTimes >= IC_INVOKE_TIMES_GROUP) {
                    object.setIcInvokeTimes(object.getIcInvokeTimes() + 1);
                    logD( "action: ic invoke + 1 ");
                    mIcInvokeRealTimes = 0;
                }
                break;
            case ACTION_GET_TAC_INVOKE:
                object.setTacInvokeTimes(object.getTacInvokeTimes() + 1);
                logD("action: tac invoke + 1 ");
                break;
            case ACTION_GET_TBC_INVOKE:
                object.setTbcInvokeTimes(object.getTbcInvokeTimes() + 1);
                logD("action: tbc invoke + 1 ");
                break;
            case ACTION_GET_SEL_INVOKE:
                object.setSelectTextInvokeTimes(object.getSelectTextInvokeTimes() + 1);
                logD( "action: sel invoke + 1 ");
                break;
            case ACTION_GET_EXT_INVOKE:
                object.setExtractedTextInvokeTimes(object.getExtractedTextInvokeTimes() + 1);
                logD("action: ext invoke + 1 ");
                break;
            case ACTION_HIT_TAC_CACHE:
                object.setHitTacCacheTimes(object.getHitTacCacheTimes() + 1);
                logD("action: hit tac cache invoke + 1 ");
                break;
            case ACTION_HIT_TBC_CACHE:
                object.setHitTbcCacheTimes(object.getHitTbcCacheTimes() + 1);
                logD("action: hit tbc cache invoke + 1 ");
                break;
            case ACTION_HIT_SEL_CACHE:
                object.setHitSelCacheTimes(object.getHitSelCacheTimes() + 1);
                logD( "action: hit sel cache invoke + 1 ");
                break;
            case ACTION_HIT_EXT_CACHE:
                object.setHitExtCacheTimes(object.getHitExtCacheTimes() + 1);
                logD("action: hit ext cache invoke + 1 ");
                break;
            case ACTION_RESET_INVOKE:
                object.setResetTimes(object.getResetTimes() + 1);
                logD( "action: reset invoke + 1 ");
                break;
            case ACTION_ON_FINISH_RESET_INVOKE:
                object.setResetDueToFinishInvokeTimes(object.getResetDueToFinishInvokeTimes() + 1);
                logD( "action: reset on finish invoke + 1 ");
                break;
            case ACTION_INIT_BUFFER_INVOKE:
                object.setInitBufferInvokeTimes(object.getInitBufferInvokeTimes() + 1);
                logD("action: init buffer + 1 ");
                break;
            case ACTION_INIT_BUFFER_ON_START_INVOKE:
                object.setInitBufferWhenStartInvokeTimes(object.getInitBufferWhenStartInvokeTimes() + 1);
                logD( "action: init buffer on start + 1 ");
                break;

            case ACTION_DIFF_GET_TAC_INVOKE:
                if(object.getDiffTac() == null){
                    object.setDiffTac(new ArrayList<>());
                }
                object.setDiffTacTimes(object.getDiffTacTimes() + 1);
                putDetailItem(object.getDiffTac(), event);
                if(BuildConfig.DEBUG) {
                    logD("action: diff get tac + 1 " + "expected: " + event.getParam().getArgString0() + " ;actual:" + event.getParam().getArgString1()+"; pkg: "+event.getParam().getArgString3());
                }
                break;
            case ACTION_DIFF_GET_TBC_INVOKE:
                if(object.getDiffTbc() == null){
                    object.setDiffTbc(new ArrayList<>());
                }
                object.setDiffTbcTimes(object.getDiffTbcTimes() + 1);
                putDetailItem(object.getDiffTbc(), event);
                if(BuildConfig.DEBUG) {
                    logD("action: diff get tbc + 1 " + "expected: " + event.getParam().getArgString0() + " ;actual:" + event.getParam().getArgString1());
                }
                break;
            case ACTION_DIFF_GET_SEL_INVOKE:
                if(object.getDiffSel() == null){
                    object.setDiffSel(new ArrayList<>());
                }
                object.setDiffSelectedTextTimes(object.getDiffSelectedTextTimes() + 1);
                putDetailItem(object.getDiffSel(), event);
                if(BuildConfig.DEBUG) {
                    logD("action: diff get sel + 1 " + "expected: " + event.getParam().getArgString0() + " ;actual:" + event.getParam().getArgString1());
                }
                break;
            case ACTION_DIFF_GET_EXT_INVOKE:
                if(object.getDiffExt() == null){
                    object.setDiffExt(new ArrayList<>());
                }
                object.setDiffExtractedTextTimes(object.getDiffExtractedTextTimes() + 1);
                putDetailItem(object.getDiffExt(), event);
                if(BuildConfig.DEBUG) {
                    logD("action: diff get ext + 1 " + "expected: " + event.getParam().getArgString0() + " ;actual:" + event.getParam().getArgString1());
                }
                break;
            default:
                break;
        }

        return object;
    }

    private void logD(String msg){
        if(BuildConfig.DEBUG) {
            Log.d("IcEvaluationOperation", msg);
        }
    }

    private void putDetailItem(List<DiffIcCompareBean> diffItems, EventProto event){
        if(diffItems.size() >= MAX_RECORD_DIFF_ITEMS_COUNT){
            return;
        }
        DiffIcCompareBean diffBean = new DiffIcCompareBean();
        diffBean.setExpected(event.getParam().getArgString0());
        diffBean.setActual(event.getParam().getArgString1());
        diffBean.setCursor(event.getParam().getArgString2());
        diffBean.setPackageName(event.getParam().getArgString3());
        diffItems.add(diffBean);
    }
}
