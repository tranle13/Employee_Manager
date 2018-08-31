
	// Name: Tran Le
	// JAV2 - 1809
	// File name: PrefFragment.java

package com.sunny.android.letran_ce02.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.sunny.android.letran_ce02.DatabaseHelper;
import com.sunny.android.letran_ce02.R;

public class PrefFragment extends PreferenceFragment {

	// Variable to hold key value of Display Preference
	public static final String KEY_DISPLAY = "pref_display";
	private static final String KEY_DELETE = "pref_data";

	// Code to set up preference layout
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	// Function to set click listener to delete preference
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Preference deletePref = findPreference(KEY_DELETE);
		if (deletePref != null) {
			deletePref.setOnPreferenceClickListener(dataPrefTapped);
		}
	}

	// Set click listener for the Delete Preference
	private final Preference.OnPreferenceClickListener dataPrefTapped = new Preference.OnPreferenceClickListener() {
		@Override
		public boolean onPreferenceClick(Preference preference) {
			if (getActivity() != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.data_subtitle);
				builder.setMessage(R.string.dialog_message);
				builder.setNegativeButton(R.string.dialog_neg_button, null);
				builder.setPositiveButton(R.string.data_subtitle, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
						helper.deleteAll();
						Toast.makeText(getActivity(), "All record deleted", Toast.LENGTH_SHORT).show();
					}
				});
				builder.show();
			}
			return false;
		}
	};
}
