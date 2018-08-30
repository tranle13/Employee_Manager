
	// Name: Tran Le
	// JAV2 - 1809
	// File name: ListViewFragment.java

package com.sunny.android.letran_ce02.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sunny.android.letran_ce02.EmployeeAdapter;
import com.sunny.android.letran_ce02.R;

public class ListViewFragment extends ListFragment {

	private Cursor eCursor;
	private TheChosen chosenEmployee;

	public ListViewFragment() {
		// Default empty constructor
	}

	// Interface to communicate with host context
	public interface TheChosen {
		void getChosen(int position);
	}

	public Cursor getCursor() {
		return eCursor;
	}

	public void setCursor(Cursor eCursor) {
		this.eCursor = eCursor;
	}

	public static ListViewFragment newInstance(Cursor cursor) {

		Bundle args = new Bundle();

		ListViewFragment fragment = new ListViewFragment();
		fragment.setArguments(args);
		fragment.setCursor(cursor);
		return fragment;
	}

	// Check if the host context implements interface
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof TheChosen) {
			chosenEmployee = (TheChosen)context;
		}
	}

	// Return the view to fragment
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listview_fragment, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getView() != null && getCursor() != null) {
			setListAdapter(new EmployeeAdapter(getContext(), getCursor()));
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (chosenEmployee != null) {
			chosenEmployee.getChosen(position);
		}
	}
}
