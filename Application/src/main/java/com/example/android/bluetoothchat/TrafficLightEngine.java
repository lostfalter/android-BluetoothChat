package com.example.android.bluetoothchat;

import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficLightEngine {
    public static final int STATE_RED = 0;
    public static final int STATE_YELLOW = 1;
    public static final int STATE_GREEN = 2;

    public static final int STATE_UNKNOWN = 100;

    private int mState = STATE_UNKNOWN;

    private Handler mHandler;

    private Timer mTimer;
    private TimerTask mTask = null;

    private static final String TAG = "TrafficLightEngine";

    public TrafficLightEngine(Handler handler) {
        mTimer = new Timer();

        mHandler = handler;
    }

    public synchronized void setState(int state) {
        if (mState != state) {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }

            mState = state;
            notifyCurrentState();
        }
    }

    public void stop() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }

        mTimer.cancel();
        mTimer = null;
    }

    private synchronized void notifyCurrentState() {
        Log.i(TAG, "Current state: " + getStateString(mState));
        mHandler.obtainMessage(Constants.MESSAGE_REFRESH_TRAFFIC_LIGHT, mState, -1).sendToTarget();
        mTask = new TimerTask() {
            @Override
            public void run() {
                notifyCurrentState();
            }
        };

        mTimer.schedule(mTask,10 * 1000);
    }

    private String getStateString(int state) {
        switch (state) {
            case STATE_RED:
                return "red";
            case STATE_YELLOW:
                return "yellow";
            case STATE_GREEN:
                return "green";
            default:
                return "unknown";
        }
    }
}
