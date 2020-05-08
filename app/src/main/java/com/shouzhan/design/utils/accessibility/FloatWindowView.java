package com.shouzhan.design.utils.accessibility;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.shouzhan.design.App;
import com.shouzhan.design.R;
import com.shouzhan.design.utils.OSUtil;

/**
 * @author danbin
 * @version FloatWindowView.java, v 0.1 2020-05-08 9:24 AM danbin
 */
public class FloatWindowView {

    private Handler mHandler;

    private View mView;

    private WindowManager mWindowManager;

    private Context mContext;

    private ProgressBar mProgressBar;

    private static class FloatWindowBuilder {

        public static FloatWindowView mFloatWindowView = new FloatWindowView();

    }

    private FloatWindowView() {
        this.mContext = App.getInstance();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.mView = LayoutInflater.from(mContext).inflate(R.layout.layout_accessibility_float_window, (ViewGroup) null);
        ImageView imageView = mView.findViewById(R.id.iv_voice_diagnosis_loading);
        this.mProgressBar = mView.findViewById(R.id.voice_diagnosis_progress);
        this.mProgressBar.setMax(200);
        ((AnimationDrawable) imageView.getBackground()).start();
    }

    public static FloatWindowView getInstance() {
        return FloatWindowBuilder.mFloatWindowView;
    }

    public void m40649a(WindowManager.LayoutParams layoutParams) {
        OSUtil.ROM_TYPE c = OSUtil.getRomType();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = 2038;
        } else if (c == OSUtil.ROM_TYPE.MIUI_ROM) {
            layoutParams.type = 2003;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            layoutParams.type = 2003;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            layoutParams.type = 2005;
        } else if (c == OSUtil.ROM_TYPE.COLOROS_ROM) {
            layoutParams.type = 2003;
        } else {
            layoutParams.type = 2005;
        }
    }

    public void build() {
        this.mHandler.post(() -> {
            try {
                FloatWindowView.this.mProgressBar.setProgress(0);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                FloatWindowView.this.m40649a(layoutParams);
                layoutParams.width = -1;
                layoutParams.height = -1;
                layoutParams.format = 1;
                layoutParams.flags = 8;
                layoutParams.screenOrientation = 1;
                layoutParams.gravity = 17;
                FloatWindowView.this.mWindowManager.addView(FloatWindowView.this.mView, layoutParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void remove() {
        this.mHandler.post(() -> {
            try {
                if (!(FloatWindowView.this.mWindowManager == null || FloatWindowView.this.mView == null)) {
                    FloatWindowView.this.mWindowManager.removeViewImmediate(FloatWindowView.this.mView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setProgress(final int progress) {
        this.mHandler.post(() -> {
            if (FloatWindowView.this.mProgressBar != null) {
                FloatWindowView.this.mProgressBar.setProgress(progress);
            }
        });
    }

}
