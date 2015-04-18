package com.mohamedibrahim.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohamedibrahim.ToDoList.BaseProvider.DATABASE;
import com.mohamedibrahim.ToDoList.R;
import com.mohamedibrahim.ToDoList.ToDoItem;

public class MyListAdapter extends CursorListViewAdapter {

	private LayoutInflater inflater;

	public MyListAdapter(Context context, Cursor cursor, String IdColumnIndex) {
		super(context, cursor, IdColumnIndex);

		inflater = LayoutInflater.from(getContext());

	}

	@Override
	public View onCreateViewHolder(View convertView, ViewGroup parent, Cursor c) {

		VH holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.todolist_item, parent,
					false);
			holder = new VH(convertView);
			convertView.setTag(holder);
		} else {

			holder = (VH) convertView.getTag();
		}

		ToDoItem item = new ToDoItem(c.getString(c
				.getColumnIndex(DATABASE.KEY_TASK)));

		holder.row.setText(item.getTask());
		holder.rowDate.setText(item.getFormateCreatedDate());

		return convertView;
	}

	private static class VH extends ViewHolder {

		private TextView rowDate;
		private TextView row;

		public VH(View v) {
			super(v);

			rowDate = (TextView) v.findViewById(R.id.rowDate);
			row = (TextView) v.findViewById(R.id.row);

		}

	}

}
