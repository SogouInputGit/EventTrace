package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import android.text.TextUtils;

import com.sogou.event.internal.EventProto;
import com.sogou.event.internal.IEventStorage;
import com.sogou.event.operation.CustomGsonOperation;

import java.util.ArrayList;

/**
 * @author dongjianye on 2020/6/9
 */
public class SwitchArrayOperation extends CustomGsonOperation<ArrayList<String>> {

    public final static int SWITCH_ARRAY_PUT = 0;

    public interface IGetDefaultSwitch {
        public boolean getDefaultSwitch(final String key);
    }

    private final IGetDefaultSwitch mGetDefaultSwitch;

    public SwitchArrayOperation(IGetDefaultSwitch getDefaultSwitch) {
        mGetDefaultSwitch = getDefaultSwitch;
    }

    @Override
    public String getFromStorage(String key, IEventStorage storage) {
        String result = super.getFromStorage(key, storage);
        if (TextUtils.isEmpty(result)) {
            final boolean switchDefault = mGetDefaultSwitch.getDefaultSwitch(key);
            result =  "[" + (switchDefault ? "1" : "0") + "]";
        }

        return result;
    }

    @Override
    public ArrayList<String> action(ArrayList<String> object, EventProto event) {
        if (event.getParam() == null) {
            return object;
        }

        if (object == null) {
            object = new ArrayList<>();
        }

        final String value = event.getParam().getArgInt0() == 1 ? "1" : "0";
        if (object.size() >= 1 && !object.get(object.size() - 1).equals(value)) {
            object.add(value);
        } else if (object.size() <= 0) {
            object.add(value);
        }

        return object;
    }

    @Override
    public void reset(String key, IEventStorage storage) {
        final boolean switchDefault = mGetDefaultSwitch.getDefaultSwitch(key);
        storage.putString(key, "[" + (switchDefault ? "1" : "0") + "]");
    }
}