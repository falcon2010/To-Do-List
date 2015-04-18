package com.mohamedibrahim.ToDoList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.mohamedibrahim.ToDoList.BaseProvider.DATABASE;
import com.mohamedibrahim.adapter.MyListAdapter;

@SuppressLint("NewApi")
public class DatabaseSkeletonSearchActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {

	MyListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		adapter = new MyListAdapter(this, null, DATABASE.KEY_ID);

		setListAdapter(adapter);

		getLoaderManager().initLoader(0, null, this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		String[] projectnio = { DATABASE.KEY_ID, DATABASE.KEY_TASK };
		String where = null;
		String[] whereArgs = null;
		String sortOrder = DATABASE.KEY_TASK + " COLLATE LOCALIZED ASC";

		CursorLoader cursorLoader = new CursorLoader(this,
				DATABASE.CONTENT_URI, projectnio, where, whereArgs, sortOrder);

		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

		adapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.changeCursor(null);
	}

}
