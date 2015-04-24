package com.gridimagesearch.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter is the translation from the data source to the View objects. How to display data
 * on the view objects.
 * extends ArrayAdapter or you can use custom adapter
 * Specify the xml that the adapter can use, default is android.R.layout.simple_list_item_1
 * convert view: concept: it will create views/view objects only for the screen that you see
 * and it reuses the same view objects instead of creating one every single. good idea to check
 * if the convertview is null or not.
 */
public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
        super(context,R.layout.item_image_result,images);
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
        Log.d("imageAdapterclass", "get the position inisde getview method: " + position);
        ImageResult imageInfo = this.getItem(position);
        ImageView ivImage;  //---- universal Image Loader

        // convert view is the existing view that we want to reuse, otherwise inflate one
        if (convertView == null)
        {
            // create an inflator object
            LayoutInflater inflater = LayoutInflater.from(getContext());
            // use that inflator to inflate the view (item) that you created
            ivImage = (ImageView) inflater.inflate(R.layout.item_image_result,parent,false);
        }else  {
            // existing view
            // get the image first
            // clear that image so that the next images are loaded by making it transparent
            ivImage = (ImageView)convertView;
            //ivImage = (SmartImageView)convertView;
            // setting the view to a blank background
            ivImage.setImageResource(android.R.color.transparent);
        }
      Log.d("Image Result Adapter", imageInfo.getThumbUrl());  // TESTING

        Picasso.with(getContext())
                .load(imageInfo.getThumbUrl())
                .noFade()
                .into(ivImage);

        return ivImage;

    }

}
