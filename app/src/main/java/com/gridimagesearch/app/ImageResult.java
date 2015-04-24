package com.gridimagesearch.app;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * data model for the images
 * This contains the full and thumb url
 * Adds the images from jsonObject to an ArrayList
 */
public class ImageResult implements Serializable {

    // you will get the data from http request

    private String fullUrl;
    private String thumbUrl;
    public static String TAG = "ImageResult";

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public ImageResult(String fullUrl, String thumbUrl) {
        setThumbUrl(thumbUrl);
        setFullUrl(fullUrl);

    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    //constructor
    public ImageResult(JSONObject json) {
        try {
            this.thumbUrl = json.getString("src");
        } catch (JSONException e) {

        }

    }

/*  Results of the image from the browser for search query = apple
    imageJsonResults = [{"pagemap":{"cse_image":[{"src":"http:\/\/www.cs.columbia.edu\/~sedwards\
    /apple2fpga\/FPGA-DE2-small.jpg"}],
    "cse_thumbnail":[{"src":"https:\/\/encrypted-tbn0.gstatic.com\/images
    ?q=tbn:ANd9GcQ35IE0LGa49jzppeXpWzc7egpn81i2fTSPIBT6VcnhvJ7JoVynp2Ojj3Pp"


    */

    public static ArrayList<ImageResult> addFromResponseJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();

        // each item inside this jsonarray is a dict/json object
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONArray x = array.getJSONObject(i).getJSONObject("pagemap")
                        .getJSONArray("cse_thumbnail");
                Log.d("ImageResult.java","value of x = " + x);
                JSONObject imageJSonObject = x.getJSONObject(0);
                Log.d("ImageResult.java","value of imageJSonObject = " + imageJSonObject);
                Log.d("ImageResult.java", "imageJsonObject = " + imageJSonObject.toString());


                // add the response that contains the image to the arraylist of image
                if (imageJSonObject != null) {
                    Log.d("ImageResult", "imageResult is not null");
                    results.add(new ImageResult(imageJSonObject));
                } else
                    Log.d(TAG, "There is no image for this query");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        return results;

    }
}








