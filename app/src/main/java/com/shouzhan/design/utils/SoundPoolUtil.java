package com.shouzhan.design.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.SparseIntArray;

import com.shouzhan.design.R;

/**
 * @author danbin
 * @version SoundPoolUtil.java, v 0.1 2019-09-04 10:30 danbin
 */
public class SoundPoolUtil {

    private Context mContext;

    private SparseIntArray mSoundIds = new SparseIntArray(10);

    private SoundPool mSoundPool;

    public SoundPoolUtil(Context context) {
        this.mContext = context;
        this.createSoundPool();
    }

    private void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
        initSounds();
    }

    private void initSounds() {
        mSoundIds.put(0, mSoundPool.load(mContext, R.raw.voice_zero, 1));
        mSoundIds.put(1, mSoundPool.load(mContext, R.raw.voice_one, 1));
        mSoundIds.put(2, mSoundPool.load(mContext, R.raw.voice_two, 1));
        mSoundIds.put(3, mSoundPool.load(mContext, R.raw.voice_three, 1));
        mSoundIds.put(4, mSoundPool.load(mContext, R.raw.voice_four, 1));
        mSoundIds.put(5, mSoundPool.load(mContext, R.raw.voice_five, 1));
        mSoundIds.put(6, mSoundPool.load(mContext, R.raw.voice_six, 1));
        mSoundIds.put(7, mSoundPool.load(mContext, R.raw.voice_seven, 1));
        mSoundIds.put(8, mSoundPool.load(mContext, R.raw.voice_eight, 1));
        mSoundIds.put(9, mSoundPool.load(mContext, R.raw.voice_nine, 1));
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                //.setFlags(AudioAttributes.FLAG_HW_AV_SYNC)
                //在 21<SDK<24 是没有任何问题的 但是在SDK>24(7.0 & 7.1.1) 就会出现无法播放
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(attributes)
                .build();
    }

    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    public synchronized void play(int num) {
        mSoundPool.play(mSoundIds.get(num), 1, 1, 0, 0, 1);
    }

}
