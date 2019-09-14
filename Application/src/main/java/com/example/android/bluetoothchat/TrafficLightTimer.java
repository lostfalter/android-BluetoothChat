package com.example.android.bluetoothchat;

import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TrafficLightTimer {
    public static final int STATE_RED = 0;
    public static final int STATE_YELLOW = 1;
    public static final int STATE_GREEN = 2;

    private int mState = STATE_YELLOW;

    private Handler mHandler;

    private Timer mTimer;
    private TimerTask mFlipTask = null;
    private TimerTask mNotifyTask;

    private static final String TAG = "TrafficLightTimer";

    private int mRedLightTime;
    private int mYellowLightTime;
    private int mGreenLightTime;

    private long mStartTime;
    public TrafficLightTimer(
        Handler handler, int redLightTime, int yellowLightTime, int greenLightTime) {
        mHandler = handler;

        mRedLightTime = redLightTime;
        mYellowLightTime = yellowLightTime;
        mGreenLightTime = greenLightTime;

        mTimer = new Timer();

        scheduleNextFlip();

        mNotifyTask = new TimerTask() {
            @Override
            public void run() {
                notifyState();
            }
        };
        mTimer.schedule(mNotifyTask, 0, 1000);
    }

    public void stop() {
        mFlipTask.cancel();
        mNotifyTask.cancel();
        mTimer.cancel();
    }

    private void flip() {
        int nextState = getNextState();
        switchToState(nextState);

        notifyState();
    }
    private boolean first = true;

    private int getRemainTimeOnCurrentState() {
        int totalTime = getSwitchTime(mState);
        int elapsedTime = (int)TimeUnit.SECONDS.convert(
            System.nanoTime() - mStartTime, TimeUnit.NANOSECONDS);

        return totalTime - elapsedTime;
    }

    private void notifyState() {
        mHandler.obtainMessage(
            Constants.MESSAGE_BROADCAST_TRAFFIC_LIGHT_STATE,
            mState, getRemainTimeOnCurrentState()).sendToTarget();
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
        mState = nextState;

        scheduleNextFlip();
    }

    private void scheduleNextFlip() {
        mFlipTask = new TimerTask() {
            @Override
            public void run() {
                flip();
            }
        };

        mStartTime = System.nanoTime();
        mTimer.schedule(mFlipTask, getSwitchTime(mState) * 1000);
    }

    private int getSwitchTime(int state) {
        switch (state) {
            case STATE_RED:
                return mRedLightTime;
            case STATE_YELLOW:
                return mYellowLightTime;
            case STATE_GREEN:
                return mGreenLightTime;
            default:
                return 0;
        }
    }
}
