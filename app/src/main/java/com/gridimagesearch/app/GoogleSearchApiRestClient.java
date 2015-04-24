package com.gridimagesearch.app;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Anu on 4/23/15.
 */
public class GoogleSearchApiRestClient {

    public static final String BASE_URL = "https://www.googleapis.com/customsearch/v1";
    public static String cxKey =  "017576662512468239146:omuauf_lfve";
    public static String apiKey = "AIzaSyAs9ni18SLQNmYI9jopS20Ktqj5xjV408g";


    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void  get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        Log.d("GoogleSearchApiClient", "url =" + url);
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

   /* private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }*/
}

