package com.sohu.inputmethod.foreign.pingback;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.sogou.event.EventRecord;
import com.sogou.event.Monitor;
import com.sogou.event.internal.EventParamProto;
import com.sogou.event.operation.EventDefineFactory;
import com.sogou.event.operation.OperationInstanceCreator;
import com.sogou.lib.common.utils.ContextHolder;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.EbIndex;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.EcIndex;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.LanguageDownAndActiveOperation;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.LanguageEventOperation;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.LanguageLayoutDetailOperation;
import com.sohu.inputmethod.foreign.pingback.eventtrace.etc.SwitchArrayOperation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * @author dongjianye
 * pingback模块不能引用业务层的代码
 */
@RunWith(AndroidJUnit4.class)
public class MultiLanguageStaticsHelperTest {

    public final int WAY_OF_CHANGE_LANGUAGE_KEYBOARD_SWITCH = 1;
    public final int WAY_OF_CHANGE_LANGUAGE_LONG_PRESS_EARTH_CLICK = 2;
    public final int WAY_OF_CHANGE_LANGUAGE_LONG_PRESS_EARTH_MOVE = 3;
    public final int WAY_OF_CHANGE_LANGUAGE_PRESS_EARTH = 4;

    @Before
    public void setUp() throws Exception {
        ContextHolder.injectApplicationContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void allTest() {
        EventDefineFactory.registerOperationAdapter(EventRecord.ID_DEFAULT, SwitchArrayOperation.class, new OperationInstanceCreator<SwitchArrayOperation>() {
            @Override
            public SwitchArrayOperation createInstance(Class<?> clazz) {
                return new SwitchArrayOperation(new SwitchArrayOperation.IGetDefaultSwitch() {
                    @Override
                    public boolean getDefaultSwitch(String key) {
                        if (key.equals("ea3")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
            }
        });
        EventDefineFactory.registerEventDefine(EventRecord.ID_DEFAULT, new EbIndex());
        EventDefineFactory.registerEventDefine(EventRecord.ID_DEFAULT, new EcIndex());
        EventDefineFactory.registerEventDefine(EventRecord.ID_DEFAULT, new TestMomentaryIndex());
        ec38Increase(1);
        ec38Put(1, 0, 1);
        ec36Add(1, Arrays.asList(1,2,3,4,5));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String records = EventRecord.getInstance(ContextHolder.applicationContext()).getEventRecords();
                    Log.d("EventRecord", "getAllRecords: " + records);
                    Thread.sleep(5 * 1000);
//                    EventRecord.getInstance().finishSend(true);
                } catch (Throwable e) {
                    Log.e("EventRecord", e.getMessage());
                }
            }
        }).start();

        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);
        increaseEbByItem(EbIndex.EB_language_keyboard_show_time, 1);

        EventParamProto paramProto = EventParamProto.obtain();
        paramProto.setArgInt0(1);
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction("ea3", SwitchArrayOperation.SWITCH_ARRAY_PUT, paramProto);

        EventRecord.getInstance(ContextHolder.applicationContext()).waitDone();

//        final String records = EventRecord.getInstance().getEventRecords();
//        Log.d("EventRecord", records);
//        EventRecord.getInstance().finishSend(true);

    }

    public void ec38Increase(int lanId) {
        EventParamProto eventParam = EventParamProto.obtain();
        eventParam.setArgInt0(lanId);
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_LANGUAGE_LAYOUT_SET_LOG,
                LanguageLayoutDetailOperation.ACTION_TYPE_INCREASE, eventParam);

    }

    public void ec38Put(int lanId, int from, int to) {
        EventParamProto eventParam = EventParamProto.obtain();
        eventParam.setArgInt0(lanId);
        eventParam.setArgInt1(from);
        eventParam.setArgInt2(to);

        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_LANGUAGE_LAYOUT_SET_LOG,
                LanguageLayoutDetailOperation.ACTION_TYPE_PUT, eventParam);
    }

    public void ec36Add(int source, List<Integer> lans) {
        if (lans != null && lans.size() > 0) {
            Integer[] lanArray = new Integer[lans.size()];
            lanArray = lans.toArray(lanArray);

            EventParamProto paramProto = EventParamProto.obtain();
            paramProto.setArgString0(String.valueOf(source).intern());
            paramProto.setArgIntArray(lanArray);

            EventRecord.getInstance(ContextHolder.applicationContext()).customAction(EcIndex.EC_MULTI_LANGUAGE_SETTING_DLD_AND_SELECT,
                    LanguageDownAndActiveOperation.ACTION_ADD_ITEM, paramProto);
        }
    }

    public void increaseEbByItem(final String key, int value) {
        // 这个value也是有限的字符串常量
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseOneMap(key, String.valueOf(value).intern());
    }

    public void increaseEbByItem(final String key, String value) {
        // 这个value也是有限的字符串常量
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseOneMap(key, value);
    }

    public void increaseEb(final String key) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseNumber(key);
    }

    public void increaseEcByItem(final String key, final String value) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseOneMap(key, value);
    }

    public void increaseEc(final String key, final int lanId, final int alphabetId) {
        increaseEc(key, lanId, alphabetId, 1);
    }

    public void increaseEc(final String key, final int lanId, final int alphabetId, int value) {
        // 语言id和alphabet只有有限的几个，因此直接放到常量池里面
        EventParamProto paramProto = EventParamProto.obtain();
        paramProto.setArgInt0(value);
        paramProto.setArgString0(String.valueOf(lanId).intern());
        paramProto.setArgString1(String.valueOf(alphabetId).intern());
        EventRecord.getInstance(ContextHolder.applicationContext()).customAction(key, LanguageEventOperation.ACTION_INCREASE, paramProto);
    }

    public void increaseEcTwoMap(final String key, final int first, final int second, final int step) {
        EventRecord.getInstance(ContextHolder.applicationContext()).increaseMultiMap(key, new String[]{String.valueOf(first).intern(), String.valueOf(second).intern()}, step);
    }

    private void onCommitTextInternal(int lanId, int alphabetId, int length, boolean commitTimes, boolean commitLength) {
        if (commitTimes) {
            increaseEc(EcIndex.EC_COMMIT_TIMES, lanId, alphabetId);
        }
        if (commitLength) {
            increaseEc(EcIndex.EC_COMMIT_CHARQUENCE_LENGTH, lanId, alphabetId, length);
        }
    }

    public void onCommitTextByShell(int lanId, int alphabetId, int length) {
        if (length <= 0) {
            return;
        }

        onCommitTextInternal(lanId, alphabetId, length, true, true);
    }

    private void onCommitCandidateInternal(int lanId, int alphabetId, int index) {
        switch (index){
            case 0:
                increaseEc(EcIndex.EC_COMMIT_FIRST_CANDIDATE_TIMES, lanId, alphabetId);
                break;
            case 1:
                increaseEc(EcIndex.EC_COMMIT_SECOND_CANDIDATE_TIMES, lanId, alphabetId);
                break;
            case 2:
                increaseEc(EcIndex.EC_COMMIT_THIRD_CANDIDATE_TIMES, lanId, alphabetId);
                break;
            default:
                break;
        }
    }

    public void onCommitCandidateByShell(int lanId, int alphabetId, int index) {
        onCommitCandidateInternal(lanId, alphabetId, index);
    }

    private void onCommitBySpaceInternal(int lanId, int alphabetId) {
        increaseEc(EcIndex.EC_COMMIT_FROM_SPACE_TIMES, lanId, alphabetId);
    }

    public void onCommitBySpaceByShell(int lanId, int alphabetId) {
        onCommitBySpaceInternal(lanId, alphabetId);
    }

    public void countForeignShiftOpenPingback() {
        increaseEb(EbIndex.OPEN_BOTTOMWORDS_IN_BO_TIMES);
    }

    public void countForeignShiftClosePingback() {
        increaseEb(EbIndex.CLOSE_BOTTOMWORDS_IN_BO_TIMES);
    }

    public void onAssociationCalled(int lanId, int alphabetId) {
        increaseEc(EcIndex.EC_ASSOC_CALLED_TIMES, lanId, alphabetId);
    }

    public void onAssociationShow(int lanId, int alphabetId) {
        increaseEc(EcIndex.EC_SHOW_ASSOCIATION, lanId, alphabetId);
    }
}
