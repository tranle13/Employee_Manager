package com.sunny.android.letran_ce02.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.sunny.android.letran_ce02.R;

public class PrefFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

	public static final String KEY_DISPLAY = "pref_display";

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(KEY_DISPLAY)) {
			Preference displayPref = findPreference(key);
			displayPref.setDefaultValue(sharedPreferences.getString(key, ""));
		} else {
			Preference dataPref = findPreference(key);
			dataPref.setDefaultValue(sharedPreferences.getBoolean(key, false));
		}
	}
}
