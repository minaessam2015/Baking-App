package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget);
        if(views.getLayoutId()==R.layout.list_widget){
            Intent intent=new Intent(context,WidgetRemoteService.class);
            views.setRemoteAdapter(R.id.list_widget,intent);
            Intent templateIntent=new Intent(context,WidgetIngredientService.class);
            PendingIntent pendingIntent=PendingIntent.getService(context,0,templateIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.list_widget,pendingIntent);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list_widget);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    static void updateAppWidgetIngredients(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);
        if(views.getLayoutId()==R.layout.widget_ingredients_list){
            Intent intent=new Intent(context,WidgetIngredientRemoteService.class);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.widget_ingredient_recycler,intent);
            //Intent templateIntent=new Intent(context,WidgetIngredientService.class);
           // PendingIntent pendingIntent=PendingIntent.getService(context,0,templateIntent,PendingIntent.FLAG_UPDATE_CURRENT);
           // views.setPendingIntentTemplate(R.id.widget_ingredient_recycler,pendingIntent);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_ingredient_recycler);
        }
        Log.d("BakingWidget","updateAppWidgetIngredients");
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

