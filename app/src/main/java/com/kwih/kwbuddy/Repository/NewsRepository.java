package com.kwih.kwbuddy.Repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.kwih.kwbuddy.Config;
import com.kwih.kwbuddy.Extension.HttpHandler;
import com.kwih.kwbuddy.Extension.ImageHandler;
import com.kwih.kwbuddy.Extension.RequestCallback;
import com.kwih.kwbuddy.Extension.RequestHandleCallback;
import com.kwih.kwbuddy.Extension.RequestManager;
import com.kwih.kwbuddy.Model.News;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.Exception;

/**
 * Created by ntadmin on 2/2/2017.
 */

public class NewsRepository {

    private String TAG = NewsRepository.class.getSimpleName();
    private static NewsRepository sNewsRepository;
    private List<News> mNewses;
    private HttpHandler mHttpHandler;
    private Context context;

    public static NewsRepository get(Context context)
    {
        if(sNewsRepository==null)
        {
            sNewsRepository = new NewsRepository(context);
        }
        return sNewsRepository;
    }

    public NewsRepository(Context context)
    {
        this.context = context;
        this.mNewses = new ArrayList<>();
    }

    private News dataMapping(JSONArray contents, int position) throws JSONException
    {
        try {

            JSONObject newsContent = contents.getJSONObject(position);

            String id = newsContent.getString("id");
            String topic = newsContent.getString("topic");
            String date = newsContent.getString("date");
            String content = newsContent.getString("content");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            News News = new News();
            try{
                Date formattedDate = format.parse(date);
                News.setId(position);
                News.setTopic(topic);
                News.setDate(formattedDate);
                News.setContent(content);
            }catch(ParseException e) {
                e.printStackTrace();
            }

            JSONObject photosBank = newsContent.getJSONObject("photos");
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

            News.setPhotos(photos);
            return News;

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            throw e;
        }
    }

    public void findAllActive(final RequestHandleCallback callback) throws JSONException
    {
        String url = Config.SERVICE_URL;
        mNewses.clear();
        RequestManager.getInstance(this.context.getApplicationContext());
        RequestManager.getInstance().doRequest().takeAllNews(url,new RequestCallback(){
            @Override
            public void onSuccess(JSONObject result){
                if (result != null) {
                    try {
                        JSONArray newses = result.getJSONArray("news");

                        for (int i = 0; i < newses.length(); i++) {
                            mNewses.add(dataMapping(newses,i));
                        }
                        callback.onHandle(mNewses);
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

    public News findOneById(int id)
    {
        for(int i=0;i<this.mNewses.size();i++)
        {
            News news = this.mNewses.get(i);
            if(news.getId()==id)
            {
                return news;
            }
        }
        return null;
    }

}
