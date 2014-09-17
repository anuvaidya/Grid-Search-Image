package com.gridimagesearch.app;

import android.util.Log;

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

        Log.d("ImageResult", "inside ImageResult");
        try {
          //  this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("src");
          //  this.thumbUrl = json.getString("cse_thumbnail");

        } catch (JSONException e) {
           // e.printStackTrace();
            //this.fullUrl = null;
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

                JSONArray temp;
                JSONObject pagemapJsonObject;
                JSONObject x = null;
                Log.d("ImageResult", "input from JSONArray: = "+ array.get(i).toString());

                pagemapJsonObject= array.getJSONObject(i).getJSONObject("pagemap");

                if (!pagemapJsonObject.isNull("cse_image")) {
                       temp = pagemapJsonObject.getJSONArray("cse_image");
                        x = (JSONObject) temp.get(0);
                    Log.d("ImageResult","should print items " + x.toString());
                    results.add(new ImageResult(x));
                }
                       //getJSONArray("cse_image");




                // Log.d("ImageResult", "print the json link " + array.getJSONObject(i).get("image");

                //   Log.d("ImageResult", "should print image? " + array.getJSONArray(i).get(i).toString());

                //  results.add(new ImageResult(array.getJSONObject(i).getJSONObject("image")));

                //  results.add(new ImageResult(null));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        return results;
    }

   /* public String toString()
    {
        return this.thumbUrl;
    }
*/
}