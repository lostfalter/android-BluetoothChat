package com.example.android.bluetoothchat;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class TTSProvider {
    private final String TAG = "TTSProvider";

    private TextToSpeech mSpeech;

    float mVoiceSpeed = 0.01f;

    float mVoicePitch = 1f;

    public TTSProvider(Context context) {
        mSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
//                int supported = mSpeech.setLanguage(Locale.US);
//                if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
//                    Toast.makeText(MainActivity.this, "不支持当前语言！", Toast.LENGTH_SHORT).show();
//                    Log.i(TAG, "onInit: 支持当前选择语言");
//                }else{
//
//                }

                    Log.i(TAG, "onInit: TTS引擎初始化成功");
                }
                else{
                    Log.i(TAG, "onInit: TTS引擎初始化失败");
                }
            }
        });

    }

    public void prompt(String message) {
        if (mSpeech != null) {
            mSpeech.setSpeechRate(mVoiceSpeed);
            mSpeech.setPitch(mVoicePitch);
            mSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "abc");
        }
    }

    public void stop() {
        mSpeech.stop();
        mSpeech.shutdown();
        mSpeech = null;
    }
}
