package com.kwih.kwbuddy.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import static android.text.TextUtils.substring;

/**
 * Created by ntadmin on 2/2/2017.
 */

public class News {

    public int mId;
    public String mTopic;
    public Date mDate;
    public String mContent;
    public HashMap<String, String> mPhotos;

    public News()
    {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTopic() {
        return mTopic;
    }

    public HashMap<String, String> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(HashMap<String, String> photos) {
        mPhotos = photos;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDateForOutput()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.getDate());
    }
}
