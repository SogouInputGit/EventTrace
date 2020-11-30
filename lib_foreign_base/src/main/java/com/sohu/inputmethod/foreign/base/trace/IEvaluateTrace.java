package com.sohu.inputmethod.foreign.base.trace;

import android.support.annotation.NonNull;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputConnection;

/**
 * @author feixuezheng on 2020/8/24
 */
public interface IEvaluateTrace {

    public void onMethod(@NonNull String methodName);

    public void setInputConnection(InputConnection ic);

    public void onGetTextBeforeCursorCache(CharSequence cached, int length, int flag);
    
    public void onGetTextAfterCursorCache(CharSequence cached, int length, int flag);
    
    public void onGetSelectedTextCache(CharSequence cached);

    public void onGetExtractedTextCache(ExtractedText cached, int flags);

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd);

    public void onReset();

    public void onResetDueToFinishInput();

    public void onInitBuffer();

    public void onInitBufferDueToStartInput();

    public void resetTraceCondition();

    public void updatePkgName(String pkgName);

}
