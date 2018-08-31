
	// Name: Tran Le
	// JAV2 - 1809
	// File name: PrefActivity.java

package com.sunny.android.letran_ce02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunny.android.letran_ce02.fragments.PrefFragment;

public class PrefActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref);

		// Assign preference xml to the fragment container
		getFragmentManager().beginTransaction().replace(R.id.prefFragmentHolder,
				new PrefFragment()).commit();
	}

	// Set RESULT_OK so the data can be updated when going back to Main
	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		super.onBackPressed();
	}
}
