package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import com.sogou.event.operation.BaseEventDefine;

/**
 * @author dongjianye on 2020/6/9
 */
public class EaIndex extends BaseEventDefine {

    public static final String ENGLISH_PREDICTION = "ea6";
    public static final String ENGLISH_ASSOCIATION = "ea7";
    public static final String ENGLISH_AUTO_SPACE = "ea8";
    public static final String ENGLISH_AUTO_FIRST_CAP = "ea9";
    public static final String ENGLISH_AUTO_LOCK_CAPITAL = "ea10";
    public static final String ENGLISH_HAS_NINE_MODE = "ea11";
    public static final String ENGLISH_QWERTY_DIGIT_MODE_USER = "ea12";
    public static final String ENGLISH_USE_NINE_MODE = "ea13";
    public static final String ENGLISH_CORRECT = "ea14";
    public static final String ENGLISH_SYNONYM = "ea15";

    @Override
    protected void initDefines() {
        putEventDefine("ea6", SwitchArrayOperation.class);
        putEventDefine("ea7", SwitchArrayOperation.class);
        putEventDefine("ea8", SwitchArrayOperation.class);
        putEventDefine("ea9", SwitchArrayOperation.class);
        putEventDefine("ea10", SwitchArrayOperation.class);
        putEventDefine("ea11", SwitchArrayOperation.class);
        putEventDefine("ea12", SwitchArrayOperation.class);
        putEventDefine("ea13", SwitchArrayOperation.class);
        putEventDefine("ea14", SwitchArrayOperation.class);
        putEventDefine("ea15", SwitchArrayOperation.class);
    }
}