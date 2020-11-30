package com.sogou.event.operation;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.sogou.event.Monitor;
import com.sogou.event.EventRecord;
import com.sogou.lib.common.utils.ContextHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dongjianye on 2020/6/8
 */
@RunWith(AndroidJUnit4.class)
public class OperationTest {

    @Before
    public void setUp() throws Exception {
        ContextHolder.injectApplicationContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void testEventOperationBean() {
        EventOperationBeanCache.getEventOperationBeanFromOperationClass(OneMapOperation.class, null);

        EventOperationBeanCache.getEventOperationBeanFromOperationClass(IntOperation.class, null);
    }

    @Test
    public void testIntIncrease() {
        EventDefineFactory.registerEventDefine(EventRecord.ID_DEFAULT, new TestEventDefine());
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(TestEventDefine.EB_1, 2);
    }
}
