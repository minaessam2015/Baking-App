package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.android.bakingapp.SharedData.SharedRecipes;

public class RecipeDetails extends FragmentActivity implements RecipeDetailsAdapter.ViewClickListener,StepDetailsFragment.OnImageListener{
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
            getSupportFragmentManager().beginTransaction().add(R.id.tablet_des_details,stepDetailsFragment).commit();
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
                Log.d("RecipeDetails","step Position  "+stepPosition);
                Bundle bundle=new Bundle();
                bundle.putInt("stepPosition", stepPosition-1);
                bundle.putInt("recipePosition", recipePosition);
                bundle.putBoolean("isTwoPane", isTwoPane);
                //stepDetailsFragment.setArguments(bundle);
                StepDetailsFragment fragment=new StepDetailsFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tablet_des_details,fragment).commit();

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

    @Override
    public void onImageClickListener(int stepPosition, View view) {
        StepDetailsFragment newfragment=new StepDetailsFragment();
        Bundle fragmentBundle = new Bundle();
        int size= SharedRecipes.sharedRecipes.get(recipePosition).getStepsSize();
        Log.d("StepDetails",stepPosition+"");
        if(view.getId()==R.id.next_step) {

            if(stepPosition==size ){
                return;
            }
            // else{stepPosition++;}

        }else if(view.getId()==R.id.prev_step){
            if(stepPosition<0){
                return;
            }
            //else{stepPosition--;}
        }
        Log.d("StepDetails",stepPosition+"   step after edit");
       // this.stepPosition=stepPosition;
        fragmentBundle.putInt("stepPosition", stepPosition);
        fragmentBundle.putInt("recipePosition", recipePosition);
        fragmentBundle.putBoolean("isTwoPane", false);
        newfragment.setArguments(fragmentBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.step_details_fragment,newfragment).commit();
    }
}
