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
public class Step implements Parcelable {
    @JsonField(name = "shortDescription")
    private String shortDes;
    @JsonField(name = "description")
    private String description;
    @JsonField(name = "videoURL")
    private String videoUrl;
    @JsonField(name = "thumbnailURL")
    private String imageUrl;


    public Step(){

    }

    public String getShortDes() {
        return shortDes;
    }

    public void setShortDes(String shortDes) {
        this.shortDes = shortDes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDes);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(imageUrl);
    }

    private Step(Parcel in){
        this.shortDes=in.readString();
        this.description=in.readString();
        this.videoUrl=in.readString();
        this.imageUrl=in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR= new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
