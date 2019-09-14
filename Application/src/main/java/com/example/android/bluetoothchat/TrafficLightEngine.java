package com.example.android.bluetoothchat;

import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficLightEngine {
    public static final int STATE_RED = 0;
    public static final int STATE_YELLOW = 1;
    public static final int STATE_GREEN = 2;

    private final int mInitialState = STATE_GREEN;

    private int mState = mInitialState;

    private Handler mHandler;

    private Timer mTimer;
    private TimerTask mTask = null;

    private static final String TAG = "TrafficLightEngine";

    public TrafficLightEngine(Handler handler) {
        mTimer = new Timer();

        mHandler = handler;

        flip();
    }

    public synchronized int getState() {
        return mState;
    }

    public synchronized void reset() {
        mState = mInitialState;

        flip();
    }

    private synchronized void flip() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }

        int nextState = getNextState();
        switchToState(nextState);

        notifyCurrentState();
    }

    private void notifyCurrentState() {
        Log.i(TAG, "Switch to " + getStateString(mState));
        mHandler.obtainMessage(Constants.MESSAGE_FLIP_TRAFFIC_LIGHT, mState, -1).sendToTarget();
    }

    private int getNextState() {
        int nextState = STATE_GREEN;
        switch (mState) {
        case STATE_RED:
            nextState = STATE_GREEN;
            break;
        case STATE_YELLOW:
            nextState = STATE_RED;
            break;
        case STATE_GREEN:
            nextState = STATE_YELLOW;
            break;
        }

        return nextState;
    }

    private void switchToState(int nextState) {
        mTask = new TimerTask() {
            @Override
            public void run() {
                flip();
            }
        };

        mTimer.schedule(mTask,getSwitchTime(nextState) * 1000);

        mState = nextState;
    }

    private int getSwitchTime(int state) {
        switch (state) {
            case STATE_RED:
                return 5;
            case STATE_YELLOW:
                return 3;
            case STATE_GREEN:
                return 5;
            default:
                return 0;
        }
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
