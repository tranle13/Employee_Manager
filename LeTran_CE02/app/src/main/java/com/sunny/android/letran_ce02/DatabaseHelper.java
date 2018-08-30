
	// Name: DatabaseHelper
	// JAV2 - 1809
	// File name: DatabaseHelper

package com.sunny.android.letran_ce02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

	public class DatabaseHelper extends SQLiteOpenHelper {

		private static final String TAG = "DatabaseHelper";

	// Member variables to hold database info
	private static final String DATABASE_FILENAME = "database.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "employees";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FIRST = "firstName";
	public static final String COLUMN_LAST = "lastName";
	public static final String COLUMN_NUMBER = "number";
	public static final String COLUMN_HIRE_DATE = "hireDate";
	public static final String COLUMN_STATUS = "status";

	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_FIRST + " TEXT, "
			+ COLUMN_LAST + " TEXT, "
			+ COLUMN_NUMBER + " INTEGER, "
			+ COLUMN_HIRE_DATE + " DATETIME, "
			+ COLUMN_STATUS + " TEXT)";

	private SQLiteDatabase eDB = null;
	private static DatabaseHelper eInstance = null;

	// Static function to instantiate one DatabaseHelper for all fragments
	public static DatabaseHelper getInstance(Context _context) {
		if (eInstance == null) {
			eInstance = new DatabaseHelper(_context);
		}

		Log.i(TAG, "getInstance: "+eInstance);

		return eInstance;
	}

	// Constructor
	private DatabaseHelper(Context _context) {
		super(_context, DATABASE_FILENAME, null, DATABASE_VERSION);
		eDB = getWritableDatabase();
	}

	// Function to create database
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

	// Function to insert new employee to the table
	public void insertNewEmployee(String firstName, String lastName, int number, String hireDate, String status) {
		ContentValues content = new ContentValues();
		content.put(COLUMN_FIRST, firstName);
		content.put(COLUMN_LAST, lastName);
		content.put(COLUMN_NUMBER, number);
		content.put(COLUMN_HIRE_DATE, hireDate);
		content.put(COLUMN_STATUS, status);

		eDB.insert(TABLE_NAME, null, content);
	}

	// Function to get all data from table
	public Cursor getAllData() {
		return eDB.query(TABLE_NAME, null, null, null, null,
				null, null);
	}

	// Function to get the chosen employee data from table
	public Cursor getChosenData(int _id) {
		String where = "_id = '" + _id + "'";
		return eDB.query(TABLE_NAME, null, where, null, null,
				null, null);
	}

	// Function to delete the chosen employee
	public void deleteEmployee(int _id) {
		String where = "_id = '" + _id + "'";
		eDB.delete(TABLE_NAME, where, null);
	}

	// Function to delete all employees
	public void deleteAll() {
		eDB.delete(TABLE_NAME, null, null);
	}

	// Function to update the chosen employee
	public void updateEmployee(int _id, String firstName, String lastName, int number, String date, String status) {
		String where = "_id = '" + _id + "'";
		ContentValues changedContent = new ContentValues();
		changedContent.put(COLUMN_FIRST, firstName);
		changedContent.put(COLUMN_LAST, lastName);
		changedContent.put(COLUMN_NUMBER, number);
		changedContent.put(COLUMN_HIRE_DATE, date);
		changedContent.put(COLUMN_STATUS, status);

		eDB.update(TABLE_NAME, changedContent, where, null);
	}
}
