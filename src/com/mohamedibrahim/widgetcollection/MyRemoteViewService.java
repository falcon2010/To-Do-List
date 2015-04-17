package com.mohamedibrahim.widgetcollection;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mohamedibrahim.ToDoList.BaseProvider.DATABASE;
import com.mohamedibrahim.ToDoList.R;

@SuppressLint("NewApi")
public class MyRemoteViewService extends RemoteViewsService {

	// package name
	// com.mohamedibrahim.widgetcollection.MyRemoteViewService

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		// TODO Auto-generated method stub
		return new MyRemoteViewsFactory(getApplicationContext(), intent);
	}

	// Remote view Factory

	private static class MyRemoteViewsFactory implements RemoteViewsFactory {

		private Context context;
		private Intent intent;
		private int widgetId;
		private Cursor cursor;
		private ContentResolver cr;
		private String packageName;

		public MyRemoteViewsFactory(Context applicationContext, Intent intent) {

			this.context = applicationContext;
			this.intent = intent;
			widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);

			this.cr = this.context.getContentResolver();
			this.packageName = this.context.getPackageName();

		}

		@Override
		public int getCount() {
			if (cursor != null)
				return cursor.getCount();

			return 0;
		}

		@Override
		public long getItemId(int i) {

			if (cursor != null)
				return cursor.getInt(cursor.getColumnIndex(DATABASE.KEY_ID));

			return i;
		}

		@Override
		public RemoteViews getLoadingView() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RemoteViews getViewAt(int index) {

			cursor.moveToPosition(index);

			// extract the data from cursor

			int idIdx = cursor.getColumnIndex(DATABASE.KEY_TASK);

			String task = cursor.getString(idIdx);

			RemoteViews rv = new RemoteViews(packageName,
					R.layout.my_stack_widget_item_layout);
			rv.setTextViewText(R.id.widget_text, task);
			rv.setTextViewText(R.id.widget_title_text, task);

			Intent fillInIntent = new Intent();
			fillInIntent.putExtra(Intent.EXTRA_TEXT, task);
			rv.setOnClickFillInIntent(R.id.widget_title_text, fillInIntent);
			rv.setOnClickFillInIntent(R.id.widget_text, fillInIntent);

			return rv;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			// content provider IDs should be unique and permanent

			return true;
		}

		@Override
		public void onCreate() {
			cr = this.context.getContentResolver();

			Thread thread = new Thread() {
				public void run() {
					cursor = cr.query(DATABASE.CONTENT_URI, null, null, null,
							null);
				}
			};
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}

		@Override
		public void onDataSetChanged() {

			// requery the data again

			Thread thread = new Thread() {
				public void run() {
					cursor = cr.query(DATABASE.CONTENT_URI, null, null, null,
							null);
				}
			};
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
			}

		}

		@Override
		public void onDestroy() {

			if (cursor != null) {
				cursor.close();
				cursor = null;
			}

		}

	}

}
