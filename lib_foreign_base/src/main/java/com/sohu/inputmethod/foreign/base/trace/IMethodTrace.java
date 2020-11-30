package com.sohu.inputmethod.foreign.base.trace;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dongjianye on 2020/7/7
 */
public interface IMethodTrace {

    public static final int RETRIEVE_INFO_FROM_NOT_METHOD_NOTCARE = 0;
    public static final int RETRIEVE_INFO_FROM_CACHED_IC = 1;
    public static final int RETRIEVE_INFO_FROM_MAIN_IC = 2;
    public static final int RETRIEVE_INFO_FROM_OTHERS = 3;
    public static final int RETRIEVE_INFO_FROM_VOICE_INPUT = 4;
    public static final int RETRIEVE_INFO_FROM_FLX = 5;
    public static final int RETRIEVE_INFO_FROM_WHITE_DOG = 6;
    public static final int RETRIEVE_INFO_FROM_CH_INPUT = 7;
    public static final int RETRIEVE_INFO_FROM_CH_CANDIDATE_PROCESSING = 8;
    public static final int RETRIEVE_INFO_FROM_UPDATE_SELECTION = 9;
    public static final int RETRIEVE_INFO_FROM_EXPRESSION = 10;
    public static final int RETRIEVE_INFO_FROM_CLOUD_CANDIDATE = 11;
    @IntDef({RETRIEVE_INFO_FROM_NOT_METHOD_NOTCARE,
            RETRIEVE_INFO_FROM_CACHED_IC,
            RETRIEVE_INFO_FROM_MAIN_IC,
            RETRIEVE_INFO_FROM_OTHERS,
            RETRIEVE_INFO_FROM_VOICE_INPUT,
            RETRIEVE_INFO_FROM_FLX,
            RETRIEVE_INFO_FROM_WHITE_DOG,
            RETRIEVE_INFO_FROM_CH_INPUT,
            RETRIEVE_INFO_FROM_CH_CANDIDATE_PROCESSING,
            RETRIEVE_INFO_FROM_UPDATE_SELECTION,
            RETRIEVE_INFO_FROM_EXPRESSION,
            RETRIEVE_INFO_FROM_CLOUD_CANDIDATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RetrieveIcInfoFrom {}


    /**
     * 函数每次调用的时间
     * @param methodName method simple name
     * @param cost cost time : system nano time
     */
    public void onMethod(@NonNull String methodName, int cost);

    public void onMethod(int lanId, int alphabetId, @NonNull String methodName, int cost, boolean useCache);

    public void onIcGetTextBeforeCursor(int cost, @RetrieveIcInfoFrom int from, boolean useCache);
    
    public void onIcGetTextAfterCursor(int cost, @RetrieveIcInfoFrom int from, boolean useCache);
    
    public void onIcGetSelectedText(int cost, @RetrieveIcInfoFrom int from, boolean useCache);

    public void onIcGetExtractedText(int cost, @RetrieveIcInfoFrom int from, boolean useCache);
    
    public void onIcDeleteSurroundingText(int cost);
    
    public void onIcDeleteSurroundingTextInCodePoints(int cost);


    public void onIcSetComposingText(int cost);
    
    public void onIcSetComposingRegion(int cost);
    
    public void onIcFinishComposingText(int cost);
    
    public void onIcCommitText(int cost);
    
    public void onIcSetSelection(int cost);
    
    public void onIcBeginBatchEdit(int cost);
    
    public void onIcEndBatchEdit(int cost);
    
    public void onIcSendKeyEvent(int cost);
}
