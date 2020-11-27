package com.sohu.inputmethod.foreign.pingback;

import com.sogou.event.operation.BaseEventDefine;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.SwitchArrayOperation;

/**
 * @author dongjianye on 2020/6/9
 */
class TestMomentaryIndex extends BaseEventDefine {

    @Override
    protected void initDefines() {
        putEventDefine("ea1", TestMomentaryOperation.class);
        putEventDefine("ea2", TestMomentaryOperation.class);
        putEventDefine("ea3", SwitchArrayOperation.class);
    }
}
