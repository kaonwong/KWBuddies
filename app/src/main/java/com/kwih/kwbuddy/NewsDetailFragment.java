package com.kwih.kwbuddy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kwih.kwbuddy.Model.News;
import com.kwih.kwbuddy.Repository.NewsRepository;

import org.w3c.dom.Text;

public class NewsDetailFragment extends Fragment {

    private News mNews;
    private TextView mTopic;
    private TextView mDate;
    private TextView mContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int newsId = getActivity().getIntent().getIntExtra(NewsDetailActivity.EXTRA_NEWS_ID,0);
        this.mNews= NewsRepository.get(getActivity()).findOneById(newsId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_detail,container, false);

        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_news_detail_image_container);
        if(fragment==null)
        {
            fragment = NewsDetailImageFragment.newInstance(this.mNews.getId());
            fm.beginTransaction().add(R.id.fragment_news_detail_image_container,fragment).commit();
        }


        mTopic = (TextView) view.findViewById(R.id.fragment_news_detail_topic);
        mDate = (TextView) view.findViewById(R.id.fragment_news_detail_date);
        mContent = (TextView) view.findViewById(R.id.fragment_news_detail_content);

        mTopic.setText(this.mNews.getTopic());
        mDate.setText(this.mNews.getDate().toString());
        mContent.setText(this.mNews.getContent().toString());

        return view;
    }
}
