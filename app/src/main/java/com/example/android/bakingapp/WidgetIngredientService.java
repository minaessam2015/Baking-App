package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

/**
 * Created by mina essam on 16-Jul-17.
 */

public class WidgetIngredientService extends IntentService {

    public WidgetIngredientService(){
        super("WidgetIngredientService");
    }
    @Override
    protected void onHandleIntent( Intent intent) {
        IngredientRemoteViewsFactory.recipe=intent.getExtras().getParcelable("recipe");
        AppWidgetManager manager=AppWidgetManager.getInstance(this);
        int[] appWidgetIds = manager.getAppWidgetIds(new ComponentName(this, BakingWidget.class));
        for(int appWidgetId:appWidgetIds)
        BakingWidget.updateAppWidgetIngredients(getApplicationContext(),manager,appWidgetId);
    }


}
