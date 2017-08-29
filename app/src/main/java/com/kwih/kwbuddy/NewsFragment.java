package com.kwih.kwbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.kwih.kwbuddy.Extension.BitmapLruCache;
import com.kwih.kwbuddy.Extension.RequestHandleCallback;
import com.kwih.kwbuddy.Model.News;
import com.kwih.kwbuddy.Repository.NewsRepository;

import org.json.JSONException;

import java.util.List;

public class NewsFragment extends Fragment {

    private String TAG = NewsFragment.class.getSimpleName();
    private List<News> mNewses;
    private ProgressDialog pDialog;
    private RecyclerView mNewsRecyclerView;
    private NewsAdapter mAdapter;
    private String value = "";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initpDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);

        mNewsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(mNewses==null)
        {
            loadNews();
        }else{
            updateUI();
        }
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) context;
        value="news";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private News mNews;
        private NetworkImageView mNewsImage;
        private TextView mNewsTopic;
        private TextView mNewsDate;
        private ImageLoader.ImageCache mImageCache;
        private ImageLoader mImageLoader;

        public NewsHolder(View itemView)
        {
            super(itemView);

            mImageCache = new BitmapLruCache();
            mImageLoader = new ImageLoader(Volley.newRequestQueue(getContext()), mImageCache);
            itemView.setOnClickListener(this);
            mNewsImage = (NetworkImageView) itemView.findViewById(R.id.list_item_news_image);
            mNewsDate = (TextView) itemView.findViewById(R.id.list_item_news_date);
            mNewsTopic = (TextView) itemView.findViewById(R.id.list_item_news_topic);
        }

        public void bindNews(News news)
        {
            mNews = news;
            mNewsDate.setText(news.getDateForOutput());
            mNewsTopic.setText(news.getTopic());
            if(news.getPhotos().size()>0)
            {
                String newsPhoto = (String) news.getPhotos().values().toArray()[0];
                mNewsImage.setImageUrl(newsPhoto, mImageLoader);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = NewsDetailActivity.newInstance(getActivity(),mNews.getId());
            startActivity(intent);
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder>
    {

        private List<News> mNewses;

        public NewsAdapter(List<News> newses)
        {
            this.mNewses = newses;
        }

        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_news,parent,false);
            return new NewsHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position)
        {
            News news = this.mNewses.get(position);
            holder.bindNews(news);
        }

        @Override
        public int getItemCount() {
            return mNewses.size();
        }

        public void setNewses(List<News> newses)
        {
            mNewses = newses;
        }
    }

    private void updateUI()
    {
        if(mAdapter==null)
        {
            mAdapter = new NewsAdapter(mNewses);
            mNewsRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setNewses(mNewses);
            mNewsRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void loadNews() {

        showpDialog();
        try{
            NewsRepository newsRepository = NewsRepository.get(getActivity());
            newsRepository.findAllActive(new RequestHandleCallback(){
                @Override
                public void onHandle(List<?> result){
                    mNewses=(List<News>)result;
                    hidepDialog();
                    updateUI();
                }
            });
        }catch (JSONException e)
        {
            Log.e(TAG, "Load news error: " + e.getMessage());
        }
    }

    private void initpDialog()
    {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
