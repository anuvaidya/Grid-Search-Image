package com.gridimagesearch.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity {

    private Button btnSearch;
    private EditText etSearchItem;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    private ImageResultArrayAdapter imageAdapter;
    private String query;
    private  AsyncHttpClient client;

    //public final String tag = getApplication().getPackageName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toast.makeText(getApplication(),"inside on create",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_search);

        Log.d("hello","this is the new search");
        Toast.makeText(getBaseContext(),"inside on create", Toast.LENGTH_SHORT).show();

        client = new AsyncHttpClient();
        setupViews();
        setupAdapters();
        setupListeners();
    }

    /* Listeners for search
    on click of gvResults - to display the full size of the image.


     */
    private void setupListeners() {

        // when you click on the image on a grid, you want to display in full
      //  gvResults.setOnItemClickListener(fullImageDisplayListener); TODo - undo
        gvResults.setOnScrollListener(myEndlessScrollListener);

    }

    //note: the EndlessScrollListener this is provided by a 3rd party and not the system
    private EndlessScrollListener myEndlessScrollListener = new EndlessScrollListener(){

        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            Log.d("search", "page is " + page);
            Log.d("search","totalItemsCount is " + totalItemsCount);

            //imageResults.clear();
            loadMoreData(totalItemsCount);
        }
    };




    private void setupAdapters()
    {
        //setting adapter
        Toast.makeText(getApplication(),"inside adapter",Toast.LENGTH_SHORT).show();
        imageAdapter = new ImageResultArrayAdapter(this,imageResults);
        gvResults.setAdapter(imageAdapter);

    }
    private void setupViews() {
        Toast.makeText(getApplication(),"inside view",Toast.LENGTH_SHORT).show();
        btnSearch = (Button)findViewById(R.id.btnSearch);
        etSearchItem = (EditText)findViewById(R.id.etSearchItem);
        gvResults = (GridView)findViewById(R.id.gvResults);
        btnSearch.setOnClickListener(onImageSearchListener);
    }


    private AdapterView.OnItemClickListener fullImageDisplayListener =
            new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter,View parent,int position,long rowId)
                {
                    // from this activity we want to call another acitivity that displays the
                    // full image. so we are passing that class in the 2nd parameter
                    Intent i = new Intent(getApplicationContext(),ImageFullDisplayActivity.class);
                    // we want to display the image that we click on.
                    ImageResult imageResult = imageResults.get(position);

                    // we want to pass the parameters to the new activity
                    // we pass it in the bundle.
                    // we use put Extra that takes key,value pair
                    // we want to pass the full url
                    //NOTICE WE ARE PASSING A STRING HERE as a parameter for putExtra
                    //putExtra takes only primitive types

                    //i.putExtra("url",imageResult.getFullUrl());

                    // SUPPOSE YOU WANT TO PASS AN OBJECT INSTEAD OF A STRING
                    // FOR THAT YOU HAVE USE SERIALIZABLE/PARCEABBLE
                    // WE WANT TO PASS THE IMAGE RESULT DIRECTLY
                    // FOR THAT YOU GO TO IMAGE RESULT CLASS AND ADD IMPLEMENTS SERIALIZABLE
                    // You also have to change the imageFullDisplayActivity, where you pass
                    // the imageResult object instead of string url
                    i.putExtra("result",imageResult);
                    // this will fire the intent with the parameter
                    startActivity(i);
                }

            };



    /* With this asychttpclient you can do get request:
    https://ajax.googleapis.com/ajax/services/search/images?" +"v=1.0
    rsz - size, maximum allowed from google image api is 8
        // start - offset of the start by changing this field. Instead of zero
        // if you want to start from a different page, then you can give it here
        // this get request requires 2 params.
        // get (http request,jsonresponse handler for the http request)
        // imageJsonResults array will have all the attributes from this http client
        // we are going to use the model(imageResult to parse from this jsonarrayresults*//*.
*/


    private void loadMoreData(int totalItemsCount)
    {


        client = new AsyncHttpClient();

        Toast.makeText(getBaseContext(),"totalItemsCount = " + totalItemsCount, Toast.LENGTH_SHORT).show();
       // Toast.makeText(getBaseContext(),"page = " + page, Toast.LENGTH_SHORT).show();
        query = etSearchItem.getText().toString();
        Toast.makeText(getBaseContext(),"search button is pressed " + query, Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(),"on search is pressed the first time",Toast.LENGTH_SHORT).show();

       // https://www.googleapis.com/customsearch/v1?q=apple&cx=008694621533056856355%3Ak3jmqo__vou&imgColorType=color&imgSize=small&imgType=photo&key=AIzaSyAs9ni18SLQNmYI9jopS20Ktqj5xjV408g
       // client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
         //       "start=" + totalItemsCount+ "&v=1.0&q=" + Uri.encode(query),
        client .get("https://www.googleapis.com/customsearch/v1?q="+ Uri.encode(query)+"&cx=008694621533056856355%3Ak3jmqo__vou&" +
                "&imgColorType=color&imgSize=small&imgType=photo&key=AIzaSyAs9ni18SLQNmYI9jopS20Ktqj5xjV408g",
                new JsonHttpResponseHandler() {
                    JSONArray imageJsonResults = null;
                    @Override
                    public void onSuccess(JSONObject response) {
                        //parse the data
                        try {
                            imageJsonResults = response.getJSONArray("items");
                                  //  .getJSONArray("items");
                            Toast.makeText(getBaseContext(),"responseData = " +response ,
                                    Toast.LENGTH_SHORT).show();
                            Toast.makeText(getBaseContext(),"items_array_length = " +
                                imageJsonResults.length(),Toast.LENGTH_SHORT).show();

                            Log.d("hello","imageJsonResults = " + imageJsonResults);

                            //imageResults.clear();
                            // add the images from json array to our model array imageResults
                            //fromJSONArray is a static method in ImageResult
                            imageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
                            Log.d("print the imageResults from searchActivity", imageResults.toString());

                            // note: it's a good idea not to use the adapter directly
                            // because the data model and the adapter may not be in sync at times.
                            // always update the data model and use notify data set changed.
                            imageAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });

    }

    private View.OnClickListener onImageSearchListener = new View.OnClickListener(){
        public void onClick(View v)
        {

           // query = etSearchItem.getText().toString();
           // Toast.makeText(getBaseContext(),"search button is pressed" + query, Toast.LENGTH_SHORT).show();
           // Log.d("DEBUG", imageResults.toString());
           // Toast.makeText(getBaseContext(),imageResults.toString(),Toast.LENGTH_SHORT).show();
           imageResults.clear();
           loadMoreData(0); // for the first time
            // note: it's a good idea not to use the adapter directly
            // because the data model and the adapter may not be in sync at times.
            // always update the data model and use notify data set changed.
            imageAdapter.notifyDataSetChanged();

        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
