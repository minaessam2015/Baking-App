package com.example.android.bakingapp;

/**
 * Created by mina essam on 15-Jun-17.
 */

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mina essam on 14-Jun-17.
 */
@JsonObject
public class Recipe implements Parcelable{
    @JsonField(name="image")
    private String imageUrl;
    @JsonField(name="name")
    private String name;
    @JsonField(name = "ingredients")
    private List<Ingredient> ingredients=new ArrayList<>();
    @JsonField(name="steps")
    private List<Step> steps=new ArrayList<>();

    public static Intent getFakeIntent(){
        Recipe recipe=new Recipe();
        recipe.setName("nutella");
        recipe.setImageUrl("");
        List<Ingredient> ingredients=new ArrayList<>();
        for(int i=0;i<5;++i){
            Ingredient ingredient=new Ingredient();
            ingredient.setIngredient("Tea");
            ingredient.setMeasure("OZ");
            ingredient.setQuantity(i);
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);
        Intent intent=new Intent();
        intent.putExtra("recipePosition",0);
        intent.putExtra("recipe",recipe);
        return intent;
    }
    public Recipe() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Ingredient getIngredientAt(int i){
        return ingredients.get(i);
    }

    public Step getStepAt(int i){
        return steps.get(i);
    }
    public int getStepsSize(){return steps.size();}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
    }

    private Recipe(Parcel in){
        this.imageUrl=in.readString();
        this.name=in.readString();
        in.readList(this.ingredients,Ingredient.class.getClassLoader());
        in.readList(this.steps,Step.class.getClassLoader());
    }

    public static final Parcelable.Creator<Recipe> CREATOR= new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
