package com.kwih.kwbuddy.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ntadmin on 4/19/2017.
 */

public class Album {

    public int mId;
    public String mName;
    public String mCover;
    public Date mDate;
    public String mDescription;
    public HashMap<String, String> mPhotos;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public HashMap<String, String> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(HashMap<String, String> photos) {
        mPhotos = photos;
    }
}
