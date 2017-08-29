package com.kwih.kwbuddy.Repository;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kwih.kwbuddy.Config;
import com.kwih.kwbuddy.Extension.RequestCallback;
import com.kwih.kwbuddy.Extension.RequestHandleCallback;
import com.kwih.kwbuddy.Extension.RequestManager;
import com.kwih.kwbuddy.Model.Album;
import com.kwih.kwbuddy.Model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ntadmin on 4/19/2017.
 */

public class AlbumRepository {

    private String TAG = AlbumRepository.class.getSimpleName();
    private static AlbumRepository sAlbumRepository;
    private List<Album> mAlbums;
    private Context context;

    public static AlbumRepository get(Context context)
    {
        if(sAlbumRepository==null)
        {
            sAlbumRepository = new AlbumRepository(context);
        }
        return sAlbumRepository;
    }

    public AlbumRepository(Context context)
    {
        this.context = context;
        this.mAlbums = new ArrayList<>();
    }

    private Album dataMapping(JSONArray contents, int position) throws JSONException
    {
        try {

            JSONObject albumContent = contents.getJSONObject(position);

            String id = albumContent.getString("id");
            String name = albumContent.getString("name");
            String cover = albumContent.getString("cover");
            String date = albumContent.getString("date");
            String description = albumContent.getString("description");

            Album Album = new Album();
            Album.setId(position);
            Album.setName(name);
            Album.setDate(new Date());
            Album.setCover(cover);
            Album.setDescription(description);

            JSONObject photosBank = albumContent.getJSONObject("photos");
            HashMap<String, String> photos = new HashMap<>();
            HashMap<String, Drawable> photosDrawable = new HashMap<>();
            Iterator<String> iter = photosBank.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = photosBank.get(key);
                    photos.put(key,value.toString());
                } catch (JSONException e) {
                    throw e;
                }
            }

            Album.setPhotos(photos);
            return Album;

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            throw e;
        }
    }

    public void findAllActive(final RequestHandleCallback callback) throws JSONException
    {
        String url = String.format("%1$s?type=album",Config.SERVICE_URL);
        mAlbums.clear();
        RequestManager.getInstance(this.context.getApplicationContext());
        RequestManager.getInstance().doRequest().takeAllAlbums(url,new RequestCallback(){
            @Override
            public void onSuccess(JSONObject result){
                if (result != null) {
                    try {
                        JSONArray albums = result.getJSONArray("album");

                        for (int i = 0; i < albums.length(); i++) {
                            mAlbums.add(dataMapping(albums,i));
                        }
                        callback.onHandle(mAlbums);
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                } else {
                    String message = "Couldn't get data from server.";
                    Log.e(TAG, message);
                }
            }
        });
    }
}
