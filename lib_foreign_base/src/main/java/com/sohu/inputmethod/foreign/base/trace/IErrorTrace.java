package com.sohu.inputmethod.foreign.base.trace;

/**
 * @author dongjianye on 2020/7/7
 */
public interface IErrorTrace {

    /**
     * 内核发生异常
     * @param code error code
     */
    public void onShellError(int code);


    /**
     * 客户端发生异常
     * @param code error code
     */
    public void onClientError(int code);
}
