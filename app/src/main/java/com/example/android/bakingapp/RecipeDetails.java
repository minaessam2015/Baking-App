package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class RecipeDetails extends FragmentActivity implements RecipeDetailsAdapter.ViewClickListener{
    private boolean isTwoPane=false;
    private int recipePosition;
    private StepDetailsFragment stepDetailsFragment;
    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Bundle inputBundle=getIntent().getExtras();
        recipePosition=inputBundle.getInt("recipePosition");
        recipe=inputBundle.getParcelable("recipe");
        Log.d("RecipeDetails","RecipeDetails =  "+recipePosition);
        RecipeDetailsFragment fragment=new RecipeDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("recipePosition",recipePosition);
        bundle.putParcelable("recipe",recipe);
        if(recipe==null){
            Log.d("RecipeDetails","Recipe is Null");
        }else {
            Log.d("RecipeDetails"," Recipe size  "+recipe.getIngredients().size());
        }
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.recipe_details_fragment,fragment).commit();

        if(findViewById(R.id.tablet_des_details)!=null){
            isTwoPane=true;
            //add second fragment
             stepDetailsFragment=new StepDetailsFragment();
            Bundle fragmentBundle=new Bundle();
            fragmentBundle.putBoolean("isTwoPane",isTwoPane);
            fragmentBundle.putInt("recipePosition",recipePosition);
            //open ingredients
            fragmentBundle.putInt("stepPosition",0);
            stepDetailsFragment.setArguments(fragmentBundle);
            getSupportFragmentManager().beginTransaction().add(R.id.step_details_fragment,stepDetailsFragment).commit();
        }

    }

    @Override
    public void onViewClickListener(int stepPosition) {
        if (!isTwoPane) {
            //phone open new activity
            if (stepPosition > 0) {
                //step not ingredient
                Intent intent = new Intent(this, StepDetails.class);
                intent.putExtra("isTwoPane", isTwoPane);
                intent.putExtra("stepPosition", stepPosition-1);
                intent.putExtra("recipePosition", recipePosition);
                intent.putExtra("recipe",recipe);
                Log.d("RecipeDetails"," Recipe size  "+recipe.getIngredients().size());
                startActivity(intent);

            } else {
                Intent intent=new Intent(this,IngredientDetails.class);
                intent.putExtra("recipePosition", recipePosition);
                intent.putExtra("recipe",recipe);
                Log.d("RecipeDetails"," Recipe size to ingredients  "+recipe.getIngredients().size());
                startActivity(intent);
            }
        }else{
            //open step details fragment in tablet
            if(stepPosition>0){

                Bundle bundle=new Bundle();
                bundle.putInt("stepPosition", stepPosition-1);
                bundle.putInt("recipePosition", recipePosition);
                bundle.putBoolean("isTwoPane", isTwoPane);
                stepDetailsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tablet_des_details,stepDetailsFragment).commit();

            }else {
                Bundle bundle=new Bundle();
                bundle.putInt("recipePosition", recipePosition);
                bundle.putParcelable("recipe",recipe);
                IngredientDetailsFragment fragment=new IngredientDetailsFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tablet_des_details,fragment).commit();
            }


        }
    }
}
