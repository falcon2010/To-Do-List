package com.mohamedibrahim.ToDoList;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mohamedibrahim.ToDoList.BaseProvider.DATABASE;
import com.mohamedibrahim.widget.MyWidget;

public class MainActivity extends FragmentActivity implements
		NewItemFragment.OnNewItemAddedListener,
		LoaderManager.LoaderCallbacks<Cursor> {

	private ToDoItemAdapter adapter;
	private ArrayList<ToDoItem> todoItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fm = getSupportFragmentManager();
		ToDoListFragment toDoListFragment = (ToDoListFragment) fm
				.findFragmentById(R.id.TodoListFragment);

		todoItems = new ArrayList<ToDoItem>();

		adapter = new ToDoItemAdapter(this, R.layout.todolist_item, todoItems);

		toDoListFragment.setListAdapter(adapter);

		getSupportLoaderManager().initLoader(0, null, this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		getSupportLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void onNewItemAdd(String newItem) {

		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(DATABASE.KEY_TASK, newItem);
		cr.insert(DATABASE.CONTENT_URI, values);
		getSupportLoaderManager().restartLoader(0, null, this);

		// update the ToDoList Widget

		sendBroadcast(new Intent(MyWidget.Force_WIDGET_UPDATE));

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

		CursorLoader loader = new CursorLoader(this, DATABASE.CONTENT_URI,
				null, null, null, null);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

		int KeyTaskIndex = cursor.getColumnIndex(DATABASE.KEY_TASK);
		todoItems.clear();
		while (cursor.moveToNext()) {

			ToDoItem toDoItem = new ToDoItem(cursor.getString(KeyTaskIndex));
			todoItems.add(toDoItem);

		}

		adapter.notifyDataSetChanged();

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}

}
