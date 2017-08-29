package com.kwih.kwbuddy.Extension;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by ntadmin on 2/15/2017.
 */

public interface RequestCallback{
    void onSuccess(JSONObject result);
}
