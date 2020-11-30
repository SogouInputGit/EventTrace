package com.sohu.inputmethod.foreign.base.thread;

import java.util.concurrent.Executor;

/**
 * @author jiany on 2020/4/10
 */
interface IExecutorThread extends Executor {

    void quit();

    String getCurRunnableName();

    void setCurRunnableName(String runnableName);

    String getThreadName();
}
