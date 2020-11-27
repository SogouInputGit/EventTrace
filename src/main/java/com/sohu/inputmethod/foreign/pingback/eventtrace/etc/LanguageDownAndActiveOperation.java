package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import android.support.annotation.NonNull;

import com.sogou.event.internal.EventProto;
import com.sogou.event.operation.CustomGsonOperation;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Used for ec36
 *
 * @author lvchong
 */
public class LanguageDownAndActiveOperation extends CustomGsonOperation<ArrayList<LanguageDownAndActive>> {

    public static final int ACTION_ADD_ITEM = 1;

    @Override
    public ArrayList<LanguageDownAndActive> action(ArrayList<LanguageDownAndActive> object, @NonNull EventProto event) {
        if (event.getActionType() != ACTION_ADD_ITEM || event.getParam() == null) {
            return object;
        }
        if (object == null) {
            object = new ArrayList<>();
        }
        final String src = event.getParam().getArgString0();
        final Integer[] lanArray = event.getParam().getArgIntArray();

        if (lanArray != null) {
            LanguageDownAndActive itemValue = new LanguageDownAndActive();
            itemValue.setSrc(src);
            itemValue.setLans(new ArrayList<>(Arrays.asList(lanArray)));
            object.add(itemValue);
        }

        return object;
    }
}
