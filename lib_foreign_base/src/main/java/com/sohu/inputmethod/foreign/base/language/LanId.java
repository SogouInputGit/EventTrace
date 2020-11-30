package com.sohu.inputmethod.foreign.base.language;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dongjianye on 2020-03-05
 */

@IntDef({LanId.INVALID_LANGUAGE_ID,
        LanId.LANGUAGE_TYPE_CN,
        LanId.LANGUAGE_TYPE_EN,
        LanId.LANGUAGE_TYPE_CANTON,
        LanId.LANGUAGE_TYPE_BO,
        LanId.LANGUAGE_TYPE_UG,
        LanId.LANGUAGE_TYPE_ZHUYIN,
        LanId.LANGUAGE_TYPE_TAMIL,
        LanId.LANGUAGE_TYPE_KOREAN,
        LanId.LANGUAGE_TYPE_JAPANESE,
        LanId.LANGUAGE_TYPE_CANGJIE,
        LanId.LANGUAGE_TYPE_THAI})
@Retention(RetentionPolicy.SOURCE)
public @interface LanId {
   int INVALID_LANGUAGE_ID = -1;
   int LANGUAGE_TYPE_CN = 0;
   int LANGUAGE_TYPE_EN = 1;
   int LANGUAGE_TYPE_CANTON = 3;
   int LANGUAGE_TYPE_BO = 107;
   int LANGUAGE_TYPE_UG = 188;
   int LANGUAGE_TYPE_ZHUYIN = 2;
   int LANGUAGE_TYPE_TAMIL = 180;//泰米尔
   int LANGUAGE_TYPE_KOREAN = 98;
   int LANGUAGE_TYPE_JAPANESE = 99; // 日语
   int LANGUAGE_TYPE_CANGJIE = 195;
   int LANGUAGE_TYPE_FRENCH = 126;
   int LANGUAGE_TYPE_THAI = 183;
}
