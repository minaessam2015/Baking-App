package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.LoganSquare;
import com.example.android.bakingapp.IdilingResource.MyIdlingResource;
import com.example.android.bakingapp.SharedData.SharedRecipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, MainAdapter.CardClickListener {
    private boolean isTwoPane;
    MainAdapter adapter;
    @BindView(R.id.recipe_recycler) public RecyclerView recyclerView;
    public static String  url="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    String responseString;
    List<Recipe> recipes=new ArrayList<>();
    Context context;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    Parcelable layoutState;
    int recipePosition;
    static final String recyclerState="state";
    @Nullable private MyIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //remove to use butterknife library
        recyclerView=(RecyclerView)findViewById(R.id.recipe_recycler);
        adapter=new MainAdapter(this);
        adapter.setListener(this);
        context=this;
        getIdlingResource();
        if(findViewById(R.id.tablet_main)!=null){
            isTwoPane=true;
            Log.d("MainActivity","Is Tablet");
            gridLayoutManager=new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(gridLayoutManager);
        }else {

            isTwoPane=false;
            linearLayoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        if(savedInstanceState!=null){
            Log.d("MainActivity"," layoutState is NOT NULL ");
            layoutState=savedInstanceState.getParcelable(recyclerState);
            if(isTwoPane){
                gridLayoutManager.onRestoreInstanceState(layoutState);
            }else {
                linearLayoutManager.onRestoreInstanceState(layoutState);
            }
            responseString=savedInstanceState.getString("responseString");
        }else {
            Log.d("MainActivity"," layoutState is  NULL ");
        }





    }

    @Override
    protected void onStart() {
        super.onStart();
        //volley
        //getApplicationContext to ensure that this request queue stays alive
        //during application life cycle , not tied to the activity lifecycle

        if(responseString==null) {
            Log.d("MainActivity"," from volly responseString is  NULL \n");
            idlingResource.setIdleState(false);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    responseString = response;
                    Log.d("onResponse", response);
                    restartLoader();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
        }else {
            Log.d("MainActivity"," responseString is NOT NULL \n ");
        }

        getSupportLoaderManager().initLoader(0,null,this);
        //check to see if the loader exist and restart if so
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().restartLoader(0,null,this);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isTwoPane){
            layoutState=gridLayoutManager.onSaveInstanceState();
        }else {
            layoutState=linearLayoutManager.onSaveInstanceState();
        }
        outState.putParcelable(recyclerState,layoutState);
        outState.putString("responseString",responseString);
        //outState.putInt("position",linearLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){

            layoutState=savedInstanceState.getParcelable(recyclerState);
            if(layoutState==null)Log.d("MainActivity","from onRestoreInstanceState layoutState is NULL ");
            responseString=savedInstanceState.getString("responseString");
        }

    }



    void restartLoader(){
        getSupportLoaderManager().restartLoader(0,null,this);
    }


    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
               // Toast.makeText(getContext(),"Sart Loading",Toast.LENGTH_SHORT).show();
            }

            @Override
            public Void loadInBackground() {

                    if (responseString != null) {
                        Log.d("responseString", responseString);
                        try {
                            recipes = LoganSquare.parseList(responseString, Recipe.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SharedRecipes.sharedRecipes = recipes;

                    }

                return null;
            }
        };

    }
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
       adapter.setRecipes(recipes);
        if(responseString!=null){
        Log.d("MainActivity","From Load Finish  recipes size  "+recipes.size());
        Log.d("MainActivity","From Load Finish\n"+responseString);
        idlingResource.setIdleState(true);
        recyclerView.setAdapter(adapter);}

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {
        recipes.clear();
        adapter.setRecipes(recipes);
    }

    @Override
    public void onCardClickListener(int position) {
        Intent intent=new Intent(this,RecipeDetails.class);
        intent.putExtra("recipePosition",position);
        intent.putExtra("recipe",recipes.get(position));
        startActivity(intent);
    }
    @VisibleForTesting
    @NonNull
    public MyIdlingResource getIdlingResource(){
        if(idlingResource==null) {
            idlingResource= new MyIdlingResource();
            return idlingResource;
        }
        else return idlingResource;
    }
}
