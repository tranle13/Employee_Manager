
	// Name: Tran Le
	// JAV2 - 1809
	// File name: ChangeActivity.java

package com.sunny.android.letran_ce02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sunny.android.letran_ce02.fragments.ChangeFragment;
import com.sunny.android.letran_ce02.interfaces.DismissActivity;

public class ChangeActivity extends AppCompatActivity implements DismissActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);

		// Get the chosen _id and call fragment
		Intent moveIntent = getIntent();
		Integer id = moveIntent.getIntExtra(EditActivity.KEY_CHOSEN_DATA, -1);

		getSupportFragmentManager().beginTransaction().add(R.id.changeFragmentHolder,
					ChangeFragment.newInstance(id)).commit();
	}

	// Finish view
	@Override
	public void dismissView() {
		Intent backIntent = new Intent();
		backIntent.putExtra("AH", 2);
		setResult(RESULT_OK, backIntent);
		finish();
	}
}
