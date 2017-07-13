package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.android.bakingapp.SharedData.SharedRecipes;

public class StepDetails extends FragmentActivity implements StepDetailsFragment.OnImageListener {
private int recipePosition;
    StepDetailsFragment fragment;
    private int stepPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        boolean test;
        if(savedInstanceState!=null){
            recipePosition = savedInstanceState.getInt("recipePosition");
            test= savedInstanceState.getBoolean("isTwoPane");
            stepPosition=savedInstanceState.getInt("stepPosition");

        }else {
            Bundle inputBundle = getIntent().getExtras();
            recipePosition = inputBundle.getInt("recipePosition");
            test= inputBundle.getBoolean("isTwoPane");
            stepPosition=inputBundle.getInt("stepPosition");
            Log.d("StepDetails", "" + test);
            Log.d("StepDetails",inputBundle.getInt("stepPosition")+"");
        }

        Bundle bundle=new Bundle();
        bundle.putBoolean("isTwoPane",test);
        bundle.putInt("recipePosition",recipePosition);

        bundle.putInt("stepPosition",stepPosition);

        fragment=new StepDetailsFragment();
        fragment.setArguments(bundle);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.step_details_fragment,fragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.step_details_fragment,fragment).commit();
        }

    }

    @Override
    public void onImageClickListener(int stepPosition, View view) {
        StepDetailsFragment newfragment=new StepDetailsFragment();
        Bundle fragmentBundle = new Bundle();
        int size=SharedRecipes.sharedRecipes.get(recipePosition).getStepsSize();
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
        this.stepPosition=stepPosition;
        fragmentBundle.putInt("stepPosition", stepPosition);
        fragmentBundle.putInt("recipePosition", recipePosition);
        fragmentBundle.putBoolean("isTwoPane", false);
        newfragment.setArguments(fragmentBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.step_details_fragment,newfragment).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("stepPosition", stepPosition);
        outState.putInt("recipePosition", recipePosition);
        outState.putBoolean("isTwoPane", false);
    }
}
