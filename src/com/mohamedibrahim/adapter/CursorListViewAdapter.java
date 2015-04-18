package com.mohamedibrahim.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CursorListViewAdapter<VH extends CursorListViewAdapter.ViewHolder>
		extends BaseAdapter {

	private Cursor cursor;
	private Context context;
	private String mIdColumnIndex;

	public CursorListViewAdapter(Context context, Cursor cursor,
			String IdColumnIndex) {
		this.context = context;
		this.cursor = cursor;
		this.mIdColumnIndex = IdColumnIndex;

	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {

		if (cursor != null)
			return cursor.getCount();
		return 0;
	}

	@Override
	public Object getItem(int index) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		if (cursor != null) {
			return cursor.getInt(cursor.getColumnIndex(mIdColumnIndex));
		}

		return position;
	}

	public abstract View onCreateViewHolder(View convertView, ViewGroup parent,
			Cursor c);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (cursor == null)
			throw new IllegalStateException(
					"this should only be called when the cursor is valid");

		if (!cursor.moveToPosition(position))
			throw new IllegalStateException("couldn't move cursor to position "
					+ position);

		return onCreateViewHolder(convertView, parent, cursor);
	}

	public void changeCursor(Cursor newCursor) {

		Cursor oldCursor = swapCursor(newCursor);
		if (oldCursor != null)
			oldCursor.close();

	}

	private Cursor swapCursor(Cursor newCursor) {

		if (cursor == newCursor)
			return null;

		final Cursor oldCursor = cursor;

		cursor = newCursor;

		notifyDataSetChanged();
		return oldCursor;
	}

	static abstract class ViewHolder {

		public ViewHolder(View v) {

		}

	}

}
