package com.sohu.inputmethod.foreign.pingback;

import com.sogou.event.internal.IEventStorage;
import com.sogou.event.operation.BaseMomentaryOperation;

/**
 * @author dongjianye on 2020/6/9
 */
public class TestMomentaryOperation extends BaseMomentaryOperation {

    @Override
    public String get(String key, IEventStorage storage) {
        switch (key) {
            case "ea1":
                return "1";
            case "ea2":
                return "2";
            default:
                throw new RuntimeException("TestMomentaryOperation error");
        }
    }

    @Override
    public void reset(String key, IEventStorage storage) {

    }
}
