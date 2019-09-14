package com.example.android.bluetoothchat;


import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.FragmentActivity;

public class VoiceService {
    public static final int RED_LIGHT = 0;
    public static final int YELLOW_LIGHT = 1;
    public static final int GREEN_LIGHT = 2;
    public static final int PRESS = 3;

    private TTSProvider mTTSProvider = null;
    private SoundPool mSoundPool;
    private final String TAG = "VoiceService";
    private boolean mUseTTS;
    private Context mContext;

    class SoundId {
        int RED = 0;
        int YELLOW = 1;
        int GREEN = 2;
        int PRESS = 3;
    }

    SoundId mSoundId;

    public VoiceService(Context context, boolean useTTS) {
        mContext = context;
        mUseTTS = useTTS;
        if (mUseTTS) {
            mTTSProvider = new TTSProvider(mContext);
        }

        SoundPool.Builder builder = new SoundPool.Builder();

        //可同时播放的音频流
        builder.setMaxStreams(10);

        //音频属性的Builder
        AudioAttributes.Builder attrBuild = new AudioAttributes.Builder();

        //音频类型
        attrBuild.setLegacyStreamType(AudioManager.STREAM_MUSIC);

        builder.setAudioAttributes(attrBuild.build());

        mSoundPool = builder.build();

        mSoundId = new SoundId();
        mSoundId.RED = mSoundPool.load(mContext, R.raw.red_light,1);
        mSoundId.YELLOW = mSoundPool.load(mContext, R.raw.yellow_light,1);
        mSoundId.GREEN = mSoundPool.load(mContext, R.raw.green_light,1);
        mSoundId.PRESS = mSoundPool.load(mContext, R.raw.trigger,1);
    }

    public void play(int type) {
//        switch (type) {
//            case RED_LIGHT:
//                if (mUseTTS) {
//                    mTTSProvider.prompt("红---灯");
//                } else {
//                    playRedLightWav();
//                }
//                break;
//            case YELLOW_LIGHT:
//                if (mUseTTS) {
//                    mTTSProvider.prompt("黄---灯");
//                } else {
//                    playYellowLightWav();
//                }
//                break;
//            case GREEN_LIGHT:
//                if (mUseTTS) {
//                    mTTSProvider.prompt("绿---灯");
//                } else {
//                    playGreenLightWav();
//                }
//                break;
//            case PRESS:
//                playPressEffect();
//                break;
//            default:
//                break;
//        }
    }

    public void stop() {
        if (mTTSProvider != null) {
            mTTSProvider.stop();
            mTTSProvider = null;
        }
    }

    private void playPressEffect() {
        mSoundPool.play(mSoundId.PRESS,1, 1, 0, 0, 1);
    }

    private void playRedLightWav() {
        mSoundPool.play(mSoundId.RED,1, 1, 0, 0, 1);
    }

    private void playYellowLightWav() {
        mSoundPool.play(mSoundId.YELLOW,1, 1, 0, 0, 1);
    }

    private void playGreenLightWav() {
        mSoundPool.play(mSoundId.GREEN,1, 1, 0, 0, 1);
    }
}
