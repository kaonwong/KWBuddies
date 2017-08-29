package com.kwih.kwbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NewsDetailActivity extends SingalFragmentActivity {

    public static final String EXTRA_NEWS_ID = "com.kwih.kwbuddy.news_id";

    @Override
    protected Fragment createFragment()
    {
        return new NewsDetailFragment();
    }

    public static Intent newInstance(Context packageContext, int newsId)
    {
        Intent intent = new Intent(packageContext, NewsDetailActivity.class);
        intent.putExtra(EXTRA_NEWS_ID,newsId);
        return intent;
    }

}
