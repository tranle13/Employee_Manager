
	// Name: Tran Le
	// JAV2 - 1809
	// File name: MainActivity.java

package com.sunny.android.letran_ce02;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sunny.android.letran_ce02.fragments.ListViewFragment;

public class MainActivity extends AppCompatActivity implements ListViewFragment.TheChosen {

	private DatabaseHelper helper;
	private static final String TAG = "MainActivity";
	private static final int REQUEST_NEW_EMPLOYEE = 1;
	public final Context appContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		helper = DatabaseHelper.getInstance(this);

		if (helper != null) {
			Cursor cursor = helper.getAllData();

			getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentHolder,
					ListViewFragment.newInstance(cursor)).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings_button:
				Intent settingIntent = new Intent(MainActivity.this, PrefActivity.class);
				startActivity(settingIntent);
				break;
			default:
				Intent addIntent = new Intent(MainActivity.this, ChangeActivity.class);
				startActivity(addIntent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void getChosen(int position) {

	}
}
