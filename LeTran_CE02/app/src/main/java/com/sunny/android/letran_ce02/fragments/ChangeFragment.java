
	// Name: Tran Le
	// JAV2 - 1809
	// File name: ChangeFragment.java

package com.sunny.android.letran_ce02.fragments;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sunny.android.letran_ce02.DatabaseHelper;
import com.sunny.android.letran_ce02.R;
import com.sunny.android.letran_ce02.interfaces.DismissActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChangeFragment extends Fragment implements View.OnClickListener {

	// Member variables
	private static final String DEFAULT_DATE_FORMAT = "MMMM dd, yyyy";
	private static final String KEY_FOR_ID = "FOR_ID";
	private Date hireDate;
	private DismissActivity dismissListener;
	private Integer id;

	public ChangeFragment() {
		// Default empty constructor
	}

	// Function to create new instance of fragment
	public static ChangeFragment newInstance(Integer id) {

		Bundle args = new Bundle();
		args.putInt(KEY_FOR_ID, id);

		ChangeFragment fragment = new ChangeFragment();
		fragment.setArguments(args);
		return fragment;
	}

	// Tell the fragment that we're gonna add an option menu
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	// Check if activity implements interface
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof DismissActivity) {
			dismissListener = (DismissActivity)context;
		}
	}

	// Function to assign the layout file to fragment java file
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.change_fragment, container,false);
	}

	// Function to populate data if there is an _id
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getView() != null) {
			Button pickDate = (Button) getView().findViewById(R.id.btn_Date);
			pickDate.setOnClickListener(this);

			id = getArguments().getInt(KEY_FOR_ID);

			if (id != -1) {
				EditText firstName = (EditText)getView().findViewById(R.id.etx_FirstName);
				EditText lastName = (EditText)getView().findViewById(R.id.etx_LastName);
				EditText number = (EditText)getView().findViewById(R.id.etx_Number);
				EditText status = (EditText)getView().findViewById(R.id.etx_Status);
				TextView hireDateText = (TextView)getView().findViewById(R.id.txt_Change_Date);

				DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
				Cursor cursor = helper.getChosenData(id);

				if (!cursor.moveToFirst()) {
					cursor.moveToFirst();
				}

 				firstName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST)));
				lastName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST)));
				status.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS)));
				Integer num = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMBER));
				number.setText(num.toString());
				String theHireDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_HIRE_DATE));

				// Reformat date
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
				String outputDateFormat = pref.getString(PrefFragment.KEY_DISPLAY, DEFAULT_DATE_FORMAT);
				String inputDateFormat = "yyyy-MM-dd";
				DateFormat df_input = new SimpleDateFormat(inputDateFormat, Locale.US);
				DateFormat df_output = new SimpleDateFormat(outputDateFormat, Locale.US);
				try {
					hireDate = df_input.parse(theHireDate);
					String superActualDate = df_output.format(hireDate);
					hireDateText.setText(superActualDate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Function to add menu to action bar in fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.save_menu, menu);
	}

	// Function to save inputs to database
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_save_button:
				if (getView() != null) {
					EditText firstName = (EditText)getView().findViewById(R.id.etx_FirstName);
					EditText lastName = (EditText)getView().findViewById(R.id.etx_LastName);
					EditText number = (EditText)getView().findViewById(R.id.etx_Number);
					EditText status = (EditText)getView().findViewById(R.id.etx_Status);

					try {
						int intNumber = Integer.parseInt(number.getText().toString());
						DatabaseHelper helper = DatabaseHelper.getInstance(getContext());

						DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						if (hireDate != null) {
							String theDate = df.format(hireDate);
							if (id == -1) {
								helper.insertNewEmployee(firstName.getText().toString(), lastName.getText().toString(),
										intNumber, theDate, status.getText().toString());
								Toast.makeText(getActivity(), "Employee added", Toast.LENGTH_SHORT).show();
							} else {
								helper.updateEmployee(id, firstName.getText().toString(), lastName.getText().toString(),
										intNumber, theDate, status.getText().toString());
								Toast.makeText(getActivity(), "Employee updated", Toast.LENGTH_SHORT).show();
							}
						}

						dismissListener.dismissView();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	// Code to show the DatePicker when user taps to select date
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_Date) {
			Calendar calendar = Calendar.getInstance();

			if (hireDate == null) {
				calendar.setTimeInMillis(System.currentTimeMillis());
			} else {
				calendar.setTime(hireDate);
			}

			DatePickerDialog dateDialog = new DatePickerDialog(getContext(), date,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dateDialog.show();
		}
	}

	// Function change TextView to the chosen date when user dismisses the DatePicker
	private final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
		if (getView() != null) {
			TextView date = (TextView) getView().findViewById(R.id.txt_Change_Date);
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
			String dateFormat = pref.getString(PrefFragment.KEY_DISPLAY, DEFAULT_DATE_FORMAT);

			Calendar newCalendar = Calendar.getInstance();
			newCalendar.set(year, month, dayOfMonth);

			hireDate = newCalendar.getTime();

			DateFormat df = new SimpleDateFormat(dateFormat, Locale.US);
			String theDate = df.format(hireDate);
			date.setText(theDate);
		}
		}
	};

}
