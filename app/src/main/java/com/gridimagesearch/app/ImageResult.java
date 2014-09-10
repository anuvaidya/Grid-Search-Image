package com.gridimagesearch.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Anu on 6/19/14.
 */
public class ImageResult implements Serializable{

    // you will get the data from http request

    private String fullUrl;
    private String thumbUrl;

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

   //constructor
    public ImageResult(JSONObject json)
    {
        // make sure that the parameter string in the json.getstring
        //matches exactly the one in the http request resultdata.
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");

        } catch (JSONException e) {
           // e.printStackTrace();
            this.fullUrl = null;
            this.thumbUrl = null;
        }
    }

   /* public static Collection<? extends ImageResult> fromJSONArray(JSONArray imageJsonResults) {
    }*/

    //iterate thru the array and get the results
    //
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();

        // each item inside this jsonarray is a dict/json object
        for (int i = 0; i < array.length(); i++)
        {
            try {
                /* // this constructs a new object -check the constructor
                // takes a jsonobject
                ImageResult newImageResultObject = new ImageResult(array.getJSONObject(i));
                results.add(newImageResultObject);
                 */
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return results;
    }

    public String toString()
    {
        return this.thumbUrl;
    }

}