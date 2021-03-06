package com.example.android.track.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.android.track.Adapter.AlbumAdapter;

import com.example.android.track.Model.Album;
import com.example.android.track.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private List<Album> albumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initPics();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.album_View);
        StaggeredGridLayoutManager layoutManager = new
        StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        AlbumAdapter adapter = new AlbumAdapter(albumList);
        recyclerView.setAdapter(adapter);
    }

    private void initPics(){
        Album album1 = new Album(R.drawable.default_portrait);
        albumList.add((album1));
        albumList.add((album1));
        albumList.add((album1));
        albumList.add((album1));
        albumList.add((album1));
        albumList.add((album1));
        Album album2 = new Album(R.drawable.add_pic);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
        albumList.add(album2);
    }
}
