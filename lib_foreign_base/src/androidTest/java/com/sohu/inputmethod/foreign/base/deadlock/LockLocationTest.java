package com.sohu.inputmethod.foreign.base.deadlock;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author dongjianye on 2020/6/12
 */
@RunWith(AndroidJUnit4.class)
public class LockLocationTest {

    @Test
    public void testOne() {
        int id = LockLocationManager.getInstance().getLocationId(new Throwable().getStackTrace());
        final LockLocationManager.LockStackInfo lockStackInfo = LockLocationManager.getInstance().getLocation(id);
        assertNotEquals(lockStackInfo, null);
    }
}