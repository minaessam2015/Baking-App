package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mina essam on 28-Jun-17.
 */

public class IngredientDetailsFragment extends Fragment {
    int recipePosition;
    RecyclerView recyclerView;
    Recipe recipe;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ingredient_details_fragment,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.ingredient_recycler);
        Bundle bundle=getArguments();
        if(bundle!=null){
            recipePosition=bundle.getInt("recipePosition",-1);
            recipe=bundle.getParcelable("recipe");
        }

        if(recipe==null){
            Log.d("IngedientDetailFragment","Recipe is Null");
        }else if(recipe.getIngredients()==null){
            Log.d("IngedientDetailFragment","ingredients is Null");
        }
        IngredientAdapter adapter=new IngredientAdapter(getContext(),recipePosition, recipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
