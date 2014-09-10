package com.gridimagesearch.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.image.SmartImageView;


public class ImageFullDisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_display);
        // decode the parameter from the bundle and use it.
        // we are getting the full url parameter
        //String url = getIntent().getStringExtra("url");

        //INSTEAD OF PASSING THE URL WE WANT TO PARSE THE IMAGERESULT OBJECT
        //Note that "result" is the key in the putExtra
        ImageResult ivImageResult = (ImageResult) getIntent().getSerializableExtra("result");
        // we want to access the smart image view thru which we are going to display the full url
        // instantiate smart image view
        SmartImageView ivImage = (SmartImageView)findViewById(R.id.ivResult);
        //ivImage.setImageUrl(url);
        ivImage.setImageUrl(ivImageResult.getFullUrl());

        //NOTE WHY DO WE NEED TO USE SERIALIZABLE?
        // SUPPOSE YOU WANT TO PASS THE TITLE, URL TOGETHER, THIS WAY YOU PASS BOTH THE VALUES
        //


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_full_display, menu);
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
