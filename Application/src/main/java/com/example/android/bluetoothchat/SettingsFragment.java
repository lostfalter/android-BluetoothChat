package com.example.android.bluetoothchat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment {
    private final String TAG = "SettingsFragment";

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener =
        new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//                Preference pref = findPreference(key);
//                if (pref instanceof EditTextPreference) {
//                    EditTextPreference etp = (EditTextPreference) pref;
//                    pref.setSummary(etp.getText() + " 秒");
//                }

                onCreate(null);
            }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        setSummaryOfPreference(sp, "red_light_time");
        setSummaryOfPreference(sp, "yellow_light_time");
        setSummaryOfPreference(sp, "green_light_time");
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        setSummaryOfPreference(sp, "red_light_time");
        setSummaryOfPreference(sp, "yellow_light_time");
        setSummaryOfPreference(sp, "green_light_time");
        sp.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        sp.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    private void setSummaryOfPreference(SharedPreferences sharedPreferences, String key) {
        findPreference(key).setSummary(sharedPreferences.getString(key, "") + " 秒");
    }
}
