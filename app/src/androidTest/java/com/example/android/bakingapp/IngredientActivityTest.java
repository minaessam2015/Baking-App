package com.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * Created by mina essam on 14-Jul-17.
 */
@RunWith(AndroidJUnit4.class)
public class IngredientActivityTest {

    Intent intent=Recipe.getFakeIntent();
    @Rule
    public IntentsTestRule<IngredientDetails> intentsTestRule=new IntentsTestRule<>(IngredientDetails.class);

    @Before
    public void blockExternalIntents(){
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,null));
    }

    @Test
    public void checkIntent(){
        intending(hasComponent(hasShortClassName(".RecipeDetails"))).respondWith(new
                Instrumentation.ActivityResult(Activity.RESULT_OK,intent));
        onData(anything()).inAdapterView(withId(R.id.ingredient_recycler)).atPosition(0).check(matches(withText("1 0.0 OZ Tea")));
    }
}
