package com.sohu.inputmethod.foreign.base.trace;

/**
 * @author dongjianye on 2020/7/7
 */
interface ISessionTrace {

    /**
     * 会话开始
     * @param sessionId 会话id
     * @param curTime 当前系统时间，毫秒
     * @param elapsedTime 当前系统启动时间，SystemClock.uptimes，毫秒
     */
    public void onSessionStart(int sessionId, long curTime, long elapsedTime);

    /**
     * 会话中途结点
     * @param sessionId 会话id
     * @param point 结点类型
     * @param curTime 当前系统时间，毫秒
     * @param elapsedTime 当前系统启动时间，SystemClock.uptimes，毫秒
     */
    public void onSessionPoint(int sessionId, String point, long curTime, long elapsedTime);
}
