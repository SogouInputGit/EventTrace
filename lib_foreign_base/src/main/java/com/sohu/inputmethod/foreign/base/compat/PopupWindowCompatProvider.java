package com.sohu.inputmethod.foreign.base.compat;

/**
 * @author dongjianye on 2020-01-20
 */
public class PopupWindowCompatProvider {

    private static PopupWindowCompat sCompat = null;

    public static void setCompat(PopupWindowCompat compat) {
        sCompat = compat;
    }

    public static PopupWindowCompat getCompat() {
        if (sCompat == null) {
            throw new RuntimeException("must init PopupWindowCompat on application");
        }
        return sCompat;
    }
}
