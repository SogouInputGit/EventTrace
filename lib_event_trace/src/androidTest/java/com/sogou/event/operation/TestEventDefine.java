package com.sogou.event.operation;

/**
 * @author dongjianye on 2020/6/8
 */
class TestEventDefine extends BaseEventDefine {

    public static final String EB_1 = "eb1";

    public static final String EA_1 = "ea1";

    @Override
    protected void initDefines() {
        putEventDefine(EB_1, IntOperation.class);
        putEventDefine(EA_1, TestMomentaryOperation.class);
    }
}
