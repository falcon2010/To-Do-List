package com.mohamedibrahim.widget;

/**
 * Configuration of the TextSize , TextColor 
 */
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Intent;
import android.os.Bundle;

public class MyWidgetConfigurationActivity extends Activity {

	// com.mohamedibrahim.widget.MyWidgetConfigurationActivity

	private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(1);

		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null){
			Bundle b = intent.getExtras() ;
			appWidgetId = b.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		
			 
		}
		
	       

	}
}
