
	// Name: Tran Le
	// JAV2 - 1809
	// File name: EmployeeAdapter.java

package com.sunny.android.letran_ce02;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.TextView;

public class EmployeeAdapter extends ResourceCursorAdapter {

	// Constructor
	public EmployeeAdapter(Context _context, Cursor _cursor) {
		super(_context, android.R.layout.simple_list_item_2, _cursor, 0);
	}

	// Function to assign the info in database to listview
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView txt_FirstName = (TextView)view.findViewById(android.R.id.text1);
		TextView txt_Number = (TextView)view.findViewById(android.R.id.text2);

		String first = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST));
		String number = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMBER));

		txt_FirstName.setText(first);
		txt_Number.setText(number);
	}
}
