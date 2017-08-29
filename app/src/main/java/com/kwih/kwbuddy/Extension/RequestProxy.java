package com.kwih.kwbuddy.Extension;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ntadmin on 2/15/2017.
 */

public class RequestProxy {

    private String TAG = RequestProxy.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private Response.ErrorListener mErrorListener;
    private Response.Listener<JSONObject> mListener;

    // package access constructor
    RequestProxy(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    private Response.ErrorListener getErrorListener()
    {
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    Log.i(TAG,"Error Response code: " +  error.networkResponse.statusCode);
                }
            }
        };
        return mErrorListener;
    }

    private Response.Listener<JSONObject> getResponseListener(final RequestCallback callback)
    {
        mListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
                Log.i(TAG,"Success Response: " + response.toString());
            }
        };
        return mListener;
    }

    public void takeAllNews(String URL,final RequestCallback callback)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL,null,this.getResponseListener(callback),this.getErrorListener());
        mRequestQueue.add(request);
    }

    public void takeAllAlbums(String URL,final RequestCallback callback)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL,null,this.getResponseListener(callback),this.getErrorListener());
        mRequestQueue.add(request);
    }

    public void login() {
        // login request
    }

    public void weather() {
        // weather request
    }

}
