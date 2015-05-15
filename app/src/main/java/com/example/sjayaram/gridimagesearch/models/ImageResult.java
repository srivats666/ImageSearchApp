package com.example.sjayaram.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sjayaram on 5/12/2015.
 */
public class ImageResult implements Parcelable {

    public String fullUrl;
    public String thumbUrl;
    public String title;
    public String content;

    public ImageResult(JSONObject json){
        try{
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.content = json.getString("content");

        }catch(JSONException ex){
            ex.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray jsonArray){
        ArrayList<ImageResult> results = new ArrayList();
        for(int i=0; i < jsonArray.length(); i++)
        {
            try
            {
                results.add(new ImageResult(jsonArray.getJSONObject(i)));
            }catch (JSONException ex){
                ex.printStackTrace();
            }
        }

        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullUrl);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.title);
        dest.writeString(this.content);
    }

    private ImageResult(Parcel in) {
        this.fullUrl = in.readString();
        this.thumbUrl = in.readString();
        this.title = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
