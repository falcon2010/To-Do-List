package com.mohamedibrahim.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.mohamedibrahim.ToDoList.BaseProvider.DATABASE;
import com.mohamedibrahim.ToDoList.MainActivity;
import com.mohamedibrahim.ToDoList.R;
import com.mohamedibrahim.ToDoList.ToDoItem;

/**
 * Represent the Widget
 * 
 * @author MohamedIrabhim
 * 
 */
public class MyWidget extends AppWidgetProvider {

	// use this action to force the widget to update itself
	public static final String Force_WIDGET_UPDATE = "com.mohamedibrahim.ToDoList.Force_WIDGET_UPDATE";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		// create the pending intent that will open the activity

		final Intent intent = new Intent(context, MainActivity.class);
		final PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
				0);

		// apply the click listener on both the TextViews
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.my_widget);

		views.setOnClickPendingIntent(R.id.row, pi);
		views.setOnClickPendingIntent(R.id.rowDate, pi);
		// Notify the App widget  manager to update
		appWidgetManager.updateAppWidget(appWidgetIds, views);
//		

		updateToDoList(context, appWidgetManager, appWidgetIds);

	}

	public void updateToDoList(Context context,
			AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		final ContentResolver cr = context.getContentResolver();

		final Cursor cursor = cr.query(DATABASE.CONTENT_URI, null, null, null,
				null);

		ToDoItem toDoItem = null;

		int KeyTaskIndex = cursor.getColumnIndex(DATABASE.KEY_TASK);

		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToLast()) {
				toDoItem = new ToDoItem(cursor.getString(KeyTaskIndex));

				cursor.close();

			}
		}

		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {

			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.my_widget);
			views.setTextViewText(R.id.row, toDoItem.getTask());

			Date createdDate = toDoItem.getCreated();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			String dateString = sdf.format(createdDate);
			views.setTextViewText(R.id.rowDate, dateString);

			appWidgetManager.updateAppWidget(appWidgetIds, views);

		}

	}

	// 587

	public void updateToDoList(Context context) {
		ComponentName thisWidget = new ComponentName(context, MyWidget.class);
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		updateToDoList(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		if (Force_WIDGET_UPDATE.equals(intent.getAction())) {
			// update the widget here
			updateToDoList(context);
		}

	}

}
