
	// Name: Tran Le
	// JAV2 - 1809
	// File name: ChangeActivity.java

package com.sunny.android.letran_ce02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sunny.android.letran_ce02.fragments.ChangeFragment;

public class ChangeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);

		getSupportFragmentManager().beginTransaction().add(R.id.changeFragmentHolder,
				ChangeFragment.newInstance()).commit();
	}
}
