package com.example.android.android_app;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.example.android.android_app.Class.Feed;
import com.example.android.android_app.Class.FeedAdapter;
import com.example.android.android_app.Class.RequestServer;
import com.example.android.android_app.Class.RequestServerInterface;

import java.util.List;

public class MyAlbumActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private List<Feed> feedList;

    private final static int GET_MY_FEED_OK = 0;
    private final static int GET_MY_FEED_FAILED = 1;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myAlbumToolBar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.pb_myAlbum);
        recyclerView = (RecyclerView) findViewById(R.id.myAlbum_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final RequestServerInterface requestServer = new RequestServer(handler, GET_MY_FEED_OK, GET_MY_FEED_FAILED, this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                feedList = requestServer.getMyFeed();
                // send message to main thread
                Message msg = new Message();
                msg.what = GET_MY_FEED_OK;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_MY_FEED_OK:
                    recyclerView.setAdapter(new FeedAdapter(feedList));
                    break;
                case GET_MY_FEED_FAILED:
                    break;
            }
        }
    };


}
