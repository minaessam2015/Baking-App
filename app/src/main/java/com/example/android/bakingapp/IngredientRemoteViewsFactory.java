package com.example.android.bakingapp;

import android.content.Context;
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
 * Created by mina essam on 16-Jul-17.
 */

class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    List<Recipe> recipes=new ArrayList<>();
    public static Recipe recipe;
    private String stringResponse;
    private Context context;

    public IngredientRemoteViewsFactory(Context context){
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
    }

    @Override
    public int getCount() {
      /* if(recipe==null)return 0;
        else {
           Log.d("IngredientFactory",""+recipe.getIngredients().size());
           return recipe.getIngredients().size();

       }*/
      if(recipes.size()==0)return 0;
        else {
          recipe=recipes.get(0);
          return recipes.get(0).getIngredients().size();
      }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.widget_ingredient_row);
        remoteViews.setTextViewText(R.id.ingredient_number,String.valueOf((position+1)));
        Ingredient ingredient=recipe.getIngredientAt(position);
        remoteViews.setTextViewText(R.id.widget_ingredient_content,ingredient.getQuantity()+" " +
                "   "+ingredient.getMeasure()+"  of  "+ingredient.getIngredient());

        return remoteViews;
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
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
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
