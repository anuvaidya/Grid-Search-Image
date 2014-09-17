package com.gridimagesearch.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Anu on 6/19/14.
 */
public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {


    /*
    public ImageResultArrayAdapter(Context context, int resource) {
        super(context, resource);
        // text layout is defined in the simple_list_item_1
        // this simple_list_item_1 is the text view
        // this textview you can display a string, but not an image
        //when you run this with the simplelistitem, you will notice
        // the http is listed as a text view
        // that means we have to create an xml for each item in the grid
    } */
   /* public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
        super(context,android.R.layout.simple_list_item_1,images);
    }
   */
    public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
        super(context,R.layout.item_image_result,images);

        Toast.makeText(getContext(),"inside imageResultAdapter ",Toast.LENGTH_SHORT).show();
    }

    //change the translation between the data and the view
    // translation right now will this layout and use the tostring method
    // to convert into

    // getview takes a particular item and converts to grid view
    // for that you have to Override
    // position of the item in the array
    // if the view is already instantiated then you re-use that view
    // to use it for other items (using convertView)
    // parent - access to the grid view itself

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //grab the data.
        ImageResult imageInfo = this.getItem(position);
        ImageView ivImage;  //---- universal Image Loader
        //SmartImageView ivImage;




        // convert view is the existing view that we want to reuse
        if (convertView == null)
        {
            // we want to convert the xml file and convert to java objects
            // that is done by Layout inflator
            // create an inflator object
            LayoutInflater inflater = LayoutInflater.from(getContext());
            // use that inflator to inflate the view (item) that you created
            ivImage = (ImageView) inflater.inflate(R.layout.item_image_result,parent,false);
            //ivImage = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent, false);

        }else  {
            // existing view
            // get the image first
            // clear that image so that the next images are loaded by making it transparent
            ivImage = (ImageView)convertView;
            //ivImage = (SmartImageView)convertView;
            // setting the view to a blank background
            ivImage.setImageResource(android.R.color.transparent);
        }
        // load the url from the imageresults
        // asycn load the images in the imageView

        // In Universal Image Loader: there is no lib setImageUrl.
        // you have to convert the string to uri using parse and use setImageUri
        // For Universal Image Loader
        // declare as   ImageView ivImage;
        // whereas for smartImageView
      /*  try {

             ivImage.setImageURI(Uri.parse(imageInfo.getThumbUrl()));
        }catch (Exception e)
        {
            Log.d("debug","cannot set the image url");
        } */

       // ivImage.setImageURI();imageInfo.getThumbUrl()
        Log.d("Image Result Adapter", imageInfo.getThumbUrl());  // TESTING

        //ivImage.setImageUrl(imageInfo.getThumbUrl());

        Picasso.with(getContext())
                .load(imageInfo.getThumbUrl())
                .noFade()
                .into(ivImage);

        return ivImage;

    }

}
