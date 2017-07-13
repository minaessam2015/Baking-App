package com.example.android.bakingapp;

/**
 * Created by mina essam on 15-Jun-17.
 */


import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by mina essam on 14-Jun-17.
 */
@JsonObject
public class Ingredient implements Parcelable{
    @JsonField(name="quantity")
    private float quantity;
    @JsonField(name = "measure")
    private String measure;
    @JsonField(name = "ingredient")
    private String ingredient;
    public Ingredient(){

    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    private Ingredient(Parcel in){
        this.quantity=in.readFloat();
        this.measure=in.readString();
        this.ingredient=in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR= new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
