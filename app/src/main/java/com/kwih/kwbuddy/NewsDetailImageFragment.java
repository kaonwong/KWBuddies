package com.kwih.kwbuddy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.kwih.kwbuddy.Extension.BitmapLruCache;
import com.kwih.kwbuddy.Model.News;
import com.kwih.kwbuddy.Repository.NewsRepository;

public class NewsDetailImageFragment extends Fragment {

    private static final String ARG_NEWS_ID="news_id";

    private NetworkImageView mImage;
    private News mNews;
    private ImageLoader.ImageCache mImageCache;
    private ImageLoader mImageLoader;

    public static NewsDetailImageFragment newInstance(int newsId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS_ID,newsId);

        NewsDetailImageFragment fragment = new NewsDetailImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int newsId = (int) getArguments().getSerializable(ARG_NEWS_ID);
        this.mNews= NewsRepository.get(getActivity()).findOneById(newsId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_detail_image,container, false);
        mImage = (NetworkImageView) view.findViewById(R.id.fragment_news_detail_image);
        mImageCache = new BitmapLruCache();
        mImageLoader = new ImageLoader(Volley.newRequestQueue(getContext()), mImageCache);

        if(this.mNews.getPhotos().size()>0)
        {
            String newsPhoto = (String) this.mNews.getPhotos().values().toArray()[0];
            mImage.setImageUrl(newsPhoto, mImageLoader);
        }

        return view;
    }
}
