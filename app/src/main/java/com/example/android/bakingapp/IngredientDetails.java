package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class IngredientDetails extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);
        Recipe recipe=getIntent().getExtras().getParcelable("recipe");
        if(recipe==null) Log.d("IngredientDetails","recipe is null");
        else Log.d("IngredientDetails","recipe is good");
        Bundle bundle=new Bundle();
        bundle.putParcelable("recipe",recipe);
        IngredientDetailsFragment fragment=new IngredientDetailsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.ingredient_details_fragment,fragment).commit();
    }
}
