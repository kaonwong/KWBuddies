package com.kwih.kwbuddy.Extension;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kwih.kwbuddy.R;

import java.util.ArrayList;

/**
 * Created by developer on 29/8/2017.
 */

public class NewsDetailImagePageAdapter extends PagerAdapter {

    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public NewsDetailImagePageAdapter(Context context, ArrayList<Integer> images)
    {
        this.context = context;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View myImageLayout = this.inflater.inflate(R.layout.fragment_news_detail_image_slide, container, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.fragment_news_detail_image);
        myImage.setImageResource(this.images.get(position));
        container.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
