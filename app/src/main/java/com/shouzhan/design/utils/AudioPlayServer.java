package com.shouzhan.design.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.App;

/**
 * @author danbin
 * @version AudioPlayerServer.java, v 0.1 2019-09-04 11:46 danbin
 */
public class AudioPlayServer extends IntentService  {

    public static final String TAG = AudioPlayServer.class.getSimpleName();

    private Context mContext;
    private SoundPoolUtil mSoundPool;

    public static void run(Context mContext, int num) {
        Intent intent = new Intent(mContext, AudioPlayServer.class);
        intent.putExtra("num", num);
        mContext.startService(intent);
    }

    public static void stop(Context mContext) {
        mContext.stopService(new Intent(mContext, AudioPlayServer.class));
    }

    @SuppressWarnings("unused")
    public AudioPlayServer() {
        this(TAG);
    }

    @SuppressWarnings("unused")
    public AudioPlayServer(String name) {
        super(name);
        FsLogUtil.error(TAG, "AudioPlayServer");
        mContext = App.getInstance();
        mSoundPool = new SoundPoolUtil(mContext);
    }

    @Override
    protected void onHandleIntent(@Nullable @android.support.annotation.Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        int num = intent.getIntExtra("num", -1);
        if (num == -1) {
            return;
        }
        if (mSoundPool != null) {
            mSoundPool.play(num);
        }
    }

}
