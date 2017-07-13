package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mina essam on 14-Jun-17.
 */

public class RecipeDetailsFragment extends Fragment  {
    @BindView(R.id.recipe_details_recycler)
    RecyclerView recyclerView;

    RecipeDetailsAdapter adapter;
    RecipeDetailsAdapter.ViewClickListener listener;
    private int recipePosition;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recipe_details_fragment,container,false);
        ButterKnife.bind(this,view);
        recyclerView=(RecyclerView)view.findViewById(R.id.recipe_details_recycler);
        Bundle bundle=getArguments();
        recipePosition=bundle.getInt("recipePosition");
        Log.d("StepDetailsFragment"," position  "+recipePosition);
        //add the adapter for this fragment
        //pass the position to get the exact recipe
        adapter=new RecipeDetailsAdapter(getContext(),recipePosition);
        adapter.setListener(listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(RecipeDetailsAdapter.ViewClickListener) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
