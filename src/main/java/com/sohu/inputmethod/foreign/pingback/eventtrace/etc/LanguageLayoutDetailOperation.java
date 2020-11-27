package com.sohu.inputmethod.foreign.pingback.eventtrace.etc;

import com.sogou.event.internal.EventProto;
import com.sogou.event.operation.CustomGsonOperation;

import java.util.ArrayList;

/**
 * Used for ec38
 *
 * @author lvchong
 */
public class LanguageLayoutDetailOperation extends CustomGsonOperation<ArrayList<DetailInLanguageLayout>> {

    public static final int ACTION_TYPE_INCREASE = 1;
    public static final int ACTION_TYPE_PUT = 2;

    @Override
    public ArrayList<DetailInLanguageLayout> action(ArrayList<DetailInLanguageLayout> bean, EventProto event) {
        if (event == null || event.getParam() == null) {
            return bean;
        }
        final int actionType = event.getActionType();
        if (actionType == ACTION_TYPE_INCREASE){
            int lan = event.getParam().getArgInt0();
            return increase(bean, lan);
        } else if (actionType == ACTION_TYPE_PUT){
            int lanId = event.getParam().getArgInt0();
            int from = event.getParam().getArgInt1();
            int to = event.getParam().getArgInt2();
            return put(bean, lanId, from, to);
        } else {
            return bean;
        }
    }

    public ArrayList<DetailInLanguageLayout> increase(ArrayList<DetailInLanguageLayout> beanObject, int lan) {
        DetailInLanguageLayout itemBean = null;
        for (DetailInLanguageLayout itemInBean : beanObject) {
            if (itemInBean.getLan() == lan) {
                itemBean = itemInBean;
                break;
            }
        }
        if (itemBean == null) {
            itemBean = new DetailInLanguageLayout();
            itemBean.setLan(lan);

            beanObject.add(itemBean);
        }
        itemBean.setClickCount(itemBean.getClickCount() + 1);

        return beanObject;
    }

    public ArrayList<DetailInLanguageLayout> put(ArrayList<DetailInLanguageLayout> beanObject, int lanId, int from, int to) {

        if (beanObject != null) {
            DetailInLanguageLayout itemBean = null;
            for (DetailInLanguageLayout itemInBean: beanObject){
                if (itemInBean.getLan() == lanId){
                    itemBean = itemInBean;
                    break;
                }
            }
            if (itemBean == null){
                itemBean = new DetailInLanguageLayout();
                itemBean.setLan(lanId);
                beanObject.add(itemBean);
            }

            itemBean.setSettingCount(itemBean.getSettingCount() + 1);

            ArrayList<Integer> settingsLogValue = itemBean.getSettingsLog();
            if (settingsLogValue == null){
                settingsLogValue = new ArrayList<>();
                itemBean.setSettingsLog(settingsLogValue);
            }

            if (settingsLogValue.size() > 0){
                settingsLogValue.add(to);
            } else {
                settingsLogValue.add(from);
                settingsLogValue.add(to);
            }
        }

        return beanObject;
    }
}
