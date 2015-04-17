package com.mohamedibrahim.widgetcollection;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mohamedibrahim.ToDoList.MainActivity;
import com.mohamedibrahim.ToDoList.R;

public class ToDoListCollectionWidget extends AppWidgetProvider {

	public static final String REFRESH_LIST_WIDGET = "com.mohamedibrahim.ToDoList.Force_WIDGET_UPDATE";

	@SuppressLint("NewApi")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		// iterate over the array of the active widget

		for (int i = 0; i < appWidgetIds.length; i++) {

			int appWidgetId = appWidgetIds[i];

			Intent intent = new Intent(context, MyRemoteViewService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.my_stack_widget_layout);
			views.setRemoteAdapter(R.id.widget_list_view, intent);
			views.setEmptyView(R.id.widget_list_view, R.id.wiget_empty_text);

			Intent templateIntent = new Intent(context, MainActivity.class);
			templateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					appWidgetId);
			PendingIntent templatePendingIntent = PendingIntent.getActivity(
					context, 0, templateIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			views.setPendingIntentTemplate(R.id.widget_list_view,
					templatePendingIntent);

			appWidgetManager.updateAppWidget(appWidgetIds, views);

		}

	}

}
