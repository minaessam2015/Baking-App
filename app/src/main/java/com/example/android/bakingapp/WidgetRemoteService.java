package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mina essam on 10-Jul-17.
 */

public class WidgetRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(getApplicationContext());
    }
}
class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    List<Recipe> recipes =new ArrayList<>();
    String stringResponse;
    Context context;

    private static   final String[] items= { "lorem", "ipsum", "dolor", "sit",
            "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel", "ligula",
            "vitae", "arcu", "aliquet", "mollis", "etiam", "vel", "erat", "placerat", "ante",
            "porttitor", "sodales", "pellentesque", "augue", "purus" };

    public WidgetRemoteViewsFactory(Context context){
        this.context=context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getResponse();

        try {
            recipes= LoganSquare.parseList(stringResponse,Recipe.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        recipes.clear();
    }

    @Override
    public int getCount() {
        if(recipes==null)return 0;
        return recipes.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_row_item);
        views.setTextViewText(R.id.widget_row_item,recipes.get(position).getName());
        Bundle bundle=new Bundle();
        bundle.putInt("recipePosition",position);
        bundle.putParcelable("recipe",recipes.get(position));
        Intent intent=new Intent();
        intent.putExtras(bundle);
        views.setOnClickFillInIntent(R.id.widget_row_item,intent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        onDataSetChanged();
    }

    void getResponse()  {
        URL url=null;
        HttpURLConnection httpURLConnection=null;

        try {
            url=new URL(MainActivity.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpURLConnection=(HttpURLConnection)url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            Scanner scanner=new Scanner(inputStream).useDelimiter("\\A");
            if(scanner.hasNext()){
                stringResponse= scanner.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
        }
    }
}