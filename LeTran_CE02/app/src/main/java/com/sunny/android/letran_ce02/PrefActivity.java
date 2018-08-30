package com.sunny.android.letran_ce02;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunny.android.letran_ce02.fragments.PrefFragment;


public class PrefActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref);

		getFragmentManager().beginTransaction().replace(R.id.prefFragmentHolder,
				new PrefFragment()).commit();
	}
//
//
//	@Override
//	public void onBuildHeaders(List<Header> target) {
//		super.onBuildHeaders(target);
//		loadHeadersFromResource(R.xml.preference_headers, target);
//	}
//
//	@Override
//	protected boolean isValidFragment(String fragmentName) {
//		return PrefFragment.class.getName().equals(fragmentName);
//	}

}
