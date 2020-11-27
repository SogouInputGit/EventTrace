package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import com.sogou.event.internal.EventProto;
import com.sogou.event.operation.CustomGsonOperation;

import java.util.ArrayList;

/**
 * 数据格式
 * [{"i":101,"l":0,"n":32},{"i":1,"l":2,"n":22}]
 *
 * @author lvchong
 */
public class LanguageEventOperation extends CustomGsonOperation<ArrayList<LanguageLayoutEvent>> {

    public final static int ACTION_INCREASE = 1;

    @Override
    public ArrayList<LanguageLayoutEvent> action(ArrayList<LanguageLayoutEvent> object, EventProto event) {
        if (event == null || event.getActionType() != ACTION_INCREASE || event.getParam() == null) {
            return object;
        }

        if (object == null) {
            object = new ArrayList<>();
        }

        final int step = event.getParam().getArgInt0();
        final int lanId = event.getParam().getArgInt1();
        final int alphabetId = event.getParam().getArgInt2();

        boolean find = false;
        for (LanguageLayoutEvent bean : object) {
            if (lanId == bean.i && alphabetId == bean.l) {
                bean.n = bean.n + step;
                find = true;
                break;
            }
        }
        if (!find) {
            LanguageLayoutEvent bean = new LanguageLayoutEvent();
            bean.i = lanId;
            bean.l = alphabetId;
            bean.n = step;
            object.add(bean);
        }

        return object;
    }
}