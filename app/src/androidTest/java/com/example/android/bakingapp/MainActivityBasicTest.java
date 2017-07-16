package com.example.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by mina essam on 13-Jul-17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule=new ActivityTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource(){
        idlingResource=activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void recipeClick_openRecipeDetailsActivity(){
        //onData(anything()).inAdapterView(withId(R.id.recipe_recycler)).atPosition(0).perform(click());
        //onData(anything()).inAdapterView(withId(R.id.recipe_details_recycler)).atPosition(0).check(matches(withText("Ingredient")));
        onView(withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.recipe_details_recycler)).check(matches(isDisplayed()));
        onView(withText("Ingredients")).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource(){
        if(idlingResource!=null)
        Espresso.unregisterIdlingResources(idlingResource);
    }

}
