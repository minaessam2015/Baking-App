package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        if(findViewById(R.id.tablet_main)!=null){
            isTwoPane=true;
            recyclerView.setLayoutManager(new GridLayoutManager(this,GridLayoutManager.DEFAULT_SPAN_COUNT));
        }else {

            isTwoPane=false;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerView.setAdapter(adapter);
        //volley
        //getApplicationContext to ensure that this request queue stays alive
        //during application life cycle , not tied to the activity lifecycle
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseString=response;
                Log.d("onResponse",response);
               /* try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonRecipe=response.getJSONObject(i);
                        String recipeName=jsonRecipe.getString("name");
                        //extract the
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }*/
               restartLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        getSupportLoaderManager().initLoader(0,null,this);
        //check to see if the loader exist and restart if so
        if(getSupportLoaderManager().getLoader(0)!=null){
          //  getSupportLoaderManager().restartLoader(0,null,this);
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
                if (responseString != null ) {
                    Log.d("responseString",responseString);
                    try {
                        recipes = LoganSquare.parseList(responseString, Recipe.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SharedRecipes.sharedRecipes=recipes;

                }
                return null;
            }
        };
    }
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
       adapter.setRecipes(recipes);
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
}
