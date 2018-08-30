
	// Name: Tran Le
	// JAV2 - 1809
	// File name: EditFragment.java

package com.sunny.android.letran_ce02.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sunny.android.letran_ce02.R;

	public class EditFragment extends Fragment {

	public EditFragment() {
		// Default empty constructor
	}

	// Function to create new instance of fragment
	public static EditFragment newInstance() {

		Bundle args = new Bundle();

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

		// Function to assign layout file to fragment
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.edit_fragment, container, false);
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
		switch (item.getItemId()) {
			case R.id.menu_edit_button:
				break;
			case R.id.menu_delete_button:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
