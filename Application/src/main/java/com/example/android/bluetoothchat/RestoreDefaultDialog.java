package com.example.android.bluetoothchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

public class RestoreDefaultDialog extends DialogPreference {

    private Context mContext;
    public RestoreDefaultDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        //super.setDialogLayoutResource(R.xml.prefs_dialog);
        //super.setDialogIcon(R.drawable.ic);
        mContext = context;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        persistBoolean(positiveResult);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        if(sp.getBoolean("resetQuests", false)) {
            // apply reset, and then set the pref-value back to false
            SharedPreferences.Editor editor= sp.edit();

            editor.clear();
            editor.apply();
            PreferenceManager.setDefaultValues(mContext, R.xml.preferences, true);
        }
    }
}