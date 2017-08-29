package com.kwih.kwbuddy;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kwih.kwbuddy.Extension.HttpHandler;
import com.kwih.kwbuddy.Extension.RequestHandleCallback;
import com.kwih.kwbuddy.Model.Album;
import com.kwih.kwbuddy.Model.News;
import com.kwih.kwbuddy.Repository.AlbumRepository;
import com.kwih.kwbuddy.Repository.NewsRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumFragment extends Fragment {

    private String TAG = AlbumFragment.class.getSimpleName();
    private GridView mGridView;
    private List<Album> mAlbums;
    private ProgressDialog pDialog;
    private SimpleAdapter mAdapter;
    private View mFragmentView;
    private String value = "";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initpDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_album,container,false);

        if(mAlbums==null)
        {
            loadAlbums();
        }else{
            updateUI();
        }

/*
            List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
            for (int i = 0; i < mAlbums.size(); i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("text", mAlbums.get(i).getName());
                items.add(item);
            }

            SimpleAdapter adapter = new SimpleAdapter(getActivity(),items, R.layout.grid_item_album, new String[]{"text"},new int[]{R.id.grid_item_album_name});
            gridView = (GridView)view.findViewById(R.id.album_fragment_gridview);
            gridView.setNumColumns(3);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), "你選擇了", Toast.LENGTH_SHORT).show();
                }
            });

*/



        return mFragmentView;
    }

    private void updateUI()
    {
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<mAlbums.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("text", mAlbums.get(i).getName());
            aList.add(hm);
        }

        String[] from = { "text"};
        int[] to = { R.id.grid_item_album_name};

        mAdapter = new SimpleAdapter(getActivity(),aList, R.layout.grid_item_album, from,to);
        mGridView = (GridView)mFragmentView.findViewById(R.id.album_fragment_gridview);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "你選擇了" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAlbums() {

        showpDialog();
        try{
            AlbumRepository albumRepository = AlbumRepository.get(getActivity());
            albumRepository.findAllActive(new RequestHandleCallback(){
                @Override
                public void onHandle(List<?> result){
                    mAlbums=(List<Album>)result;
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
