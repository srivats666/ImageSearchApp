package com.example.sjayaram.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sjayaram on 5/12/2015.
 */
public class ImageResult implements Serializable{

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
}
