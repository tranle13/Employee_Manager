
	// Name: Tran Le
	// JAV2 - 1809
	// File name: EditActivity.java

package com.sunny.android.letran_ce02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sunny.android.letran_ce02.fragments.EditFragment;
import com.sunny.android.letran_ce02.interfaces.DismissActivity;
import com.sunny.android.letran_ce02.interfaces.EditRecord;

	public class EditActivity extends AppCompatActivity implements DismissActivity, EditRecord {

	private static final String TAG = "EditActivity";
	public static final String KEY_CHOSEN_DATA = "CHOSEN_DATA";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		Intent newIntent = getIntent();
		int id = newIntent.getIntExtra(MainActivity.KEY_POSITION_ID, 0);

		Log.i(TAG, "onCreate: "+id);

		getSupportFragmentManager().beginTransaction().add(R.id.editFragmentHolder, EditFragment.newInstance(id)).commit();
	}

	@Override
	public void toChangeFragment(int id) {
		Intent toEdit = new Intent(this, ChangeActivity.class);
		toEdit.putExtra(KEY_CHOSEN_DATA, id);
		startActivity(toEdit);
	}

	@Override
	public void dismissView() {
		setResult(RESULT_OK);
		finish();
	}
}
