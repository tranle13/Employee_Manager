
	// Name: Tran Le
	// JAV2 - 1809
	// File name: MainActivity.java

package com.sunny.android.letran_ce02;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sunny.android.letran_ce02.fragments.ListViewFragment;

import java.util.ArrayList;

	public class MainActivity extends AppCompatActivity implements ListViewFragment.TheChosen {

	private DatabaseHelper helper;
	private static final String TAG = "MainActivity";
	public static final int REQUEST_NEW_EMPLOYEE = 1;
	public static final int REQUEST_VIEW_EMPLOYEE = 2;
	public static final int REQUEST_VIEW_SETTINGS = 3;
	private int numberOrStatus = -1;

	private ArrayList<Integer> ids = new ArrayList<>();

	public static final String KEY_POSITION_ID = "POSITION_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		helper = DatabaseHelper.getInstance(this);

		if (helper != null) {
			Cursor cursor = helper.getAllData();
			getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentHolder,
					ListViewFragment.newInstance(cursor)).commit();

			ids = helper.getAllId();
		}

		Spinner status = (Spinner)findViewById(R.id.spn_SortFactor);
		status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (helper != null && numberOrStatus != -1) {
					if (numberOrStatus == 0) {
						helper.sortStatus(position);
					} else {
						helper.sortNumber(position);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		Spinner way = (Spinner)findViewById(R.id.spn_SortWay);
		way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				numberOrStatus = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
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
				startActivityForResult(settingIntent, REQUEST_VIEW_SETTINGS);
				break;
			default:
				Intent addIntent = new Intent(MainActivity.this, ChangeActivity.class);
				startActivityForResult(addIntent, REQUEST_NEW_EMPLOYEE);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void getChosen(int position) {
		Log.i(TAG, "getChosen: "+position + ", size: " + ids.size());
		if (ids.size() > position) {
			Intent viewIntent = new Intent(MainActivity.this, EditActivity.class);
			viewIntent.putExtra(KEY_POSITION_ID, ids.get(position));
			startActivityForResult(viewIntent, REQUEST_VIEW_EMPLOYEE);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (helper != null) {
				Cursor cursor = helper.getAllData();
				getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentHolder,
						ListViewFragment.newInstance(cursor)).commit();

				ids = helper.getAllId();
			}
		}
	}
}
