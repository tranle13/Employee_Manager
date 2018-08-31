
	// Name: Tran Le
	// JAV2 - 1809
	// File name: MainActivity.java

package com.sunny.android.letran_ce02;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sunny.android.letran_ce02.fragments.ListViewFragment;

public class MainActivity extends AppCompatActivity implements ListViewFragment.TheChosen {

	// Member variables
	private DatabaseHelper helper;
	private static final int REQUEST_NEW_EMPLOYEE = 1;
	private static final int REQUEST_VIEW_EMPLOYEE = 2;
	private static final int REQUEST_VIEW_SETTINGS = 3;
	private int factor = 0;
	private int ways = 0;
	public static final String KEY_POSITION_ID = "POSITION_ID";
	private Spinner status;
	private Spinner way;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		helper = DatabaseHelper.getInstance(this);

		updateCursor();

		// Assign click listener to spinners
		status = (Spinner)findViewById(R.id.spn_SortFactor);
		status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (helper != null) {
					factor = position;
					updateCursor();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
		});

		way = (Spinner)findViewById(R.id.spn_SortWay);
		way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (helper != null) {
					ways = position;
					updateCursor();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
		});
	}

	// Function to create option menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Function to handle events when user taps on menu buttons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings_button:
				Intent settingIntent = new Intent(MainActivity.this, PrefActivity.class);
				startActivityForResult(settingIntent, REQUEST_VIEW_SETTINGS);
				break;
			default:
				Intent addIntent = new Intent(MainActivity.this, ChangeActivity.class);
				startActivityForResult(addIntent, REQUEST_NEW_EMPLOYEE);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	// Function to go to detail activity
	@Override
	public void getChosen(int position) {
		Intent viewIntent = new Intent(MainActivity.this, EditActivity.class);
		viewIntent.putExtra(KEY_POSITION_ID, position);
		startActivityForResult(viewIntent, REQUEST_VIEW_EMPLOYEE);
	}

	// Code to update cursor after returning from other activities
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			updateCursor();
		}
	}

	// Update data when user comes back from another view
	@Override
	protected void onResume() {
		updateCursor();
		super.onResume();
	}

	// Function to retrieve update data from database
	private void updateCursor() {
		if (helper != null && status != null && way != null) {
			Cursor cursor;

			if (factor == 0) {
				cursor = helper.sortStatus(ways);
			} else {
				cursor = helper.sortNumber(ways);
			}

			if (cursor == null) {
				cursor = helper.getAllData();
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentHolder,
					ListViewFragment.newInstance(cursor)).commit();
		}
	}
}
