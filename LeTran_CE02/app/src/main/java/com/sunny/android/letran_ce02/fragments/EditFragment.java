
	// Name: Tran Le
	// JAV2 - 1809
	// File name: EditFragment.java

package com.sunny.android.letran_ce02.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sunny.android.letran_ce02.DatabaseHelper;
import com.sunny.android.letran_ce02.R;
import com.sunny.android.letran_ce02.interfaces.DismissActivity;
import com.sunny.android.letran_ce02.interfaces.EditRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditFragment extends Fragment {

	// Member variables
	private static final String KEY_ID  = "KEY_ID";
	private Integer id;
	private DismissActivity dismissInterface;
	private EditRecord editInterface;

	public EditFragment() {
		// Default empty constructor
	}

	// Function to create new instance of fragment
	public static EditFragment newInstance(int _id) {

		Bundle args = new Bundle();

		args.putInt(KEY_ID, _id);

		EditFragment fragment = new EditFragment();
		fragment.setArguments(args);
		return fragment;
	}

	// Function to tell fragment that we're gonna have option menu
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	// Check if the host activity implements these interfaces
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof DismissActivity) {
			dismissInterface = (DismissActivity)context;
		}

		if (context instanceof EditRecord) {
			editInterface = (EditRecord)context;
		}
	}

		// Function to assign layout file to fragment
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.edit_fragment, container, false);
	}

	// Populate data from database to TextViews
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getArguments() != null && getView() != null) {

			id = getArguments().getInt(KEY_ID);
			DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
			Cursor cursor = helper.getChosenData(id);

			if (!cursor.moveToFirst()) {
				cursor.moveToFirst();
			}

			TextView firstName = (TextView)getView().findViewById(R.id.txt_FirstName);
			TextView lastName = (TextView)getView().findViewById(R.id.txt_LastName);
			TextView number = (TextView)getView().findViewById(R.id.txt_Number);
			TextView hireDate = (TextView)getView().findViewById(R.id.txt_HireDate);
			TextView status = (TextView)getView().findViewById(R.id.txt_Status);

			firstName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST)));
			lastName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST)));
			Integer num = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMBER));
			number.setText(num.toString());
			String theHireDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_HIRE_DATE));

			// Reformat date to the preference format
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
			String outputDateFormat = pref.getString(PrefFragment.KEY_DISPLAY, "MMMM dd, yyyy");
			String inputDateFormat = "yyyy-MM-dd";
			DateFormat df_input = new SimpleDateFormat(inputDateFormat, Locale.US);
			DateFormat df_output = new SimpleDateFormat(outputDateFormat, Locale.US);
			try {
				Date actualDate = df_input.parse(theHireDate);
				String superActualDate = df_output.format(actualDate);
				hireDate.setText(superActualDate);
			} catch (Exception e) {
				e.printStackTrace();
			}

			status.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS)));

		}
	}

	// Setup option menu for fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.edit_menu, menu);
	}

	// Setup event handler for option menu items
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (id != null) {
			switch (item.getItemId()) {
				case R.id.menu_edit_button:
					editInterface.toChangeFragment(id);
					break;
				case R.id.menu_delete_button:
					DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
					helper.deleteEmployee(id);
					dismissInterface.dismissView();
					Toast.makeText(getActivity(), "Employee deleted", Toast.LENGTH_SHORT).show();
					break;
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
