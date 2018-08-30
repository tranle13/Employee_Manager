package com.sunny.android.letran_ce02.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.sunny.android.letran_ce02.DatabaseHelper;
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
			if (dataPref != null) {
				dataPref.setOnPreferenceClickListener(dataPrefTapped);
			}
		}
	}

	// Set click listener for the Delete Preference
	Preference.OnPreferenceClickListener dataPrefTapped = new Preference.OnPreferenceClickListener() {
		@Override
		public boolean onPreferenceClick(Preference preference) {
			if (getActivity() != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.data_title);
				builder.setMessage(R.string.dialog_message);
				builder.setNegativeButton(R.string.dialog_neg_button, null);
				builder.setPositiveButton(R.string.data_title, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
						helper.deleteAll();
					}
				});
			}
			return false;
		}
	};
}
