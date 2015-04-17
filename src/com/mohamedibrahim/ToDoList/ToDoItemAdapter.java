package com.mohamedibrahim.ToDoList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

	private int resource;
	private LayoutInflater inflater;

	public ToDoItemAdapter(Context context, int resource, List<ToDoItem> items) {
		super(context, resource, items);
		this.resource = resource;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		VH holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(resource, parent, false);
			holder = new VH(convertView);
			convertView.setTag(holder);
		} else {
			holder = (VH) convertView.getTag();
		}

			ToDoItem item = getItem(position);
			String taskString = item.getTask();
			Date createdDate = item.getCreated();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			String dateString = sdf.format(createdDate);

		holder.row.setText(taskString);
		holder.rowData.setText(dateString);
		return convertView;
	}

	private static class VH {

		TextView rowData;
		ToDoListItemView row;

		public VH(View v) {

			rowData = (TextView) v.findViewById(R.id.rowDate);
			row = (ToDoListItemView) v.findViewById(R.id.row);
		}

	}

}