package com.shouzhan.design.dialog;

import android.support.annotation.IntDef;
import android.support.annotation.RestrictTo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * @author danbin
 * @version Priority.java, v 0.1 2019-12-19 21:22 danbin
 */
@RestrictTo(LIBRARY_GROUP)
@IntDef({Priority.PRIORITY_LOW, Priority.PRIORITY_NORMAL, Priority.PRIORITY_HEIGHT})
@Retention(RetentionPolicy.SOURCE)
public @interface Priority {

    int PRIORITY_LOW = 0x001 >> 1;
    int PRIORITY_NORMAL = 0x001;
    int PRIORITY_HEIGHT = 0x001 << 1;

}
