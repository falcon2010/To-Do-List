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

@SuppressLint("NewApi")
public class DatabaseSkeletonSearchActivity extends ListActivity implements
		LoaderCallbacks<Cursor> {

	private static final String QUERY_EXTRA_KEY = "QUERY_EXTRA_KEY";

	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String[] from = { DATABASE.KEY_TASK };
		int[] to = { android.R.id.text1 };

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null, from, to, 0);

		setListAdapter(adapter);

		getLoaderManager().initLoader(0, null, this);
		parseIntent(getIntent());

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
	public boolean onOptionsItemSelected(MenuItem item) {

		final int id = item.getItemId();
		if (id == R.id.action_settings) {

			return onSearchRequested();
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		parseIntent(getIntent());
	}

	private void parseIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String searchQuery = intent.getStringExtra(SearchManager.QUERY);
			// Perform the search
			performSearch(searchQuery);
		}

	}

	private void performSearch(String searchQuery) {
		Bundle args = new Bundle();
		args.putString(QUERY_EXTRA_KEY, searchQuery);
		// Restart the Cursor Loader to execute the new query.
		getLoaderManager().restartLoader(0, args, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		String query = "0";
		if (args != null) {
			query = args.getString(QUERY_EXTRA_KEY);
		}

		String[] projectnio = { DATABASE.KEY_ID, DATABASE.KEY_TASK };
		String where = DATABASE.KEY_TASK + " LIKE \"%" + query + "%\"";
		String[] whereArgs = null;
		String sortOrder = DATABASE.KEY_TASK + " COLLATE LOCALIZED ASC";

		CursorLoader cursorLoader = new CursorLoader(this,
				DATABASE.CONTENT_URI, projectnio, where, whereArgs, sortOrder);

		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Uri selectedUri = ContentUris.withAppendedId(DATABASE.CONTENT_URI, id);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(selectedUri);
		// Start an Activity to view the selected item.
		startActivity(intent);

	}

}
