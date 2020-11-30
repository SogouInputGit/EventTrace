package com.sogou.event.operation;

import com.sogou.event.internal.IEventStorage;

/**
 * @author dongjianye on 2020/6/8
 */
class TestMomentaryOperation extends BaseMomentaryOperation {

    @Override
    public String get(String key, IEventStorage storage) {
        return "1";
    }

    @Override
    public void reset(String key, IEventStorage storage) {

    }
}
