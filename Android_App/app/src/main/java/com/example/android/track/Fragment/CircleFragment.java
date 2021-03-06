package com.example.android.track.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.track.Activity.MyAlbumActivity;
import com.example.android.track.Adapter.FeedAdapter;
import com.example.android.track.Application.MyApplication;
import com.example.android.track.Model.Feed;
import com.example.android.track.R;
import com.example.android.track.Util.FeedRequester;
import com.example.android.track.Util.Verify;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jiguang.analytics.android.api.BrowseEvent;
import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;


/**
 * Created by thor on 2017/6/27.
 */

public class CircleFragment extends Fragment {
    private List<Feed> feedList = new ArrayList<>();
    private List<Feed> moreFeeds = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private FeedAdapter feedAdapter;

    private final static int GET_FEED_FAILED = 0;
    private final static int GET_AFTER_FEED_OK = 1 ;
    private final static int GET_BEFORE_FEED_OK = 2;
    private final static int NOT_LOG_IN = 3;
    private final static int EMPTY_RESULT = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set toolbar
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.homeToolBar);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.gray));
        toolbar.setTitle("我的圈子");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // clear new following counter
        MyApplication.setNewFollowFeed(false);
        MyApplication.setNewMsg(true);



        // set refresh layout
        swipeRefresh = (SwipeRefreshLayout) getActivity().findViewById(R.id.swip_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(feedList.size() == 0){
                    Verify verify = new Verify();
                    if(!verify.getLoged()){
                        Toast.makeText(getActivity(), "您尚未登陆", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // analytics data upload
                    CountEvent cEvent = new CountEvent("refresh_circle");
                    cEvent.addKeyValue("browser_id", "" + new Verify().getUser_id());
                    JAnalyticsInterface.onEvent(getActivity(), cEvent);

                    // if have no friend feed , refresh again
                    Date nowTime = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateStr = sdf.format(nowTime);
                    getFeeds("before", dateStr);
                    return;
                }

                String last_date = feedList.get(0).getDate();
                getFeeds("after", last_date);
            }
        });
        feedList.clear();
        firstTime_getFeed();
    }

    private void firstTime_getFeed(){
        // check internet
        ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            if (!mNetworkInfo.isAvailable()) {
                Toast.makeText(MyApplication.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(MyApplication.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(nowTime);
        getFeeds("before", dateStr);
    }


    private void getFeeds(String direction, String time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // chech if logged in
                Verify verify = new Verify();
                if (!verify.getLoged()) {
                    Message message = new Message();
                    message.what = NOT_LOG_IN;
                    handler.sendMessage(message);
                    return;
                }

                FeedRequester requester = new FeedRequester();
                moreFeeds = requester.getCircleFeed(direction, time);
                Message message = new Message();
                if (moreFeeds == null)
                    message.what = GET_FEED_FAILED;

                else if(moreFeeds.size() == 0)
                    message.what = EMPTY_RESULT;

                else {
                    if(direction.equals("after"))
                        message.what = GET_AFTER_FEED_OK;
                    else if(direction.equals("before"))
                        message.what = GET_BEFORE_FEED_OK;
                }
                handler.sendMessage(message);

            }
        }).start();
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.circle_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        FeedAdapter adapter = new FeedAdapter(getActivity(), feedList);
        recyclerView.setAdapter(adapter);
    }


    private void setRecyclerViewScrollListener(){
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的Item 的 Position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多
                        // analytics data upload
                        CountEvent cEvent = new CountEvent("refresh_circle");
                        cEvent.addKeyValue("browser_id", "" + new Verify().getUser_id());
                        JAnalyticsInterface.onEvent(getActivity(), cEvent);

                        //Toast.makeText(getActivity(), "正为您加载更多动态....", Toast.LENGTH_SHORT).show();
                        String earliestTime = feedList.get(feedList.size() -1).getDate();
                        getFeeds("before", earliestTime);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                // 如果 dx>0 则表示 右滑 ， dx<0 表示 左滑
                // dy <0 表示 上滑， dy>0 表示下滑

                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }

        });

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            swipeRefresh.setRefreshing(false);
            switch (msg.what) {
                case NOT_LOG_IN:
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "您尚未登陆", Toast.LENGTH_SHORT).show();
                    break;
                case GET_FEED_FAILED:
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "请求失败，请检查网络状态", Toast.LENGTH_SHORT).show();
                    break;
                case GET_AFTER_FEED_OK:
                    feedList.addAll(0, moreFeeds);
                    int newCnt = moreFeeds.size();
                    feedAdapter.notifyItemRangeInserted(0, newCnt);
                    if (getActivity() != null)
                        Toast.makeText(MyApplication.getContext(), newCnt + "条新动态", Toast.LENGTH_SHORT).show();
                    recyclerView.smoothScrollToPosition(0); //sroll to head
                    break;
                case GET_BEFORE_FEED_OK:
                    if (feedList.size() == 0) {  // if first time show feeds
                        feedList.addAll(moreFeeds);
                        initRecyclerView();
                    } else {
                        int old_last_index = feedList.size() - 1;
                        feedList.addAll(moreFeeds);
                        feedAdapter.notifyItemRangeInserted(old_last_index, moreFeeds.size());
                    }

                    break;

                case EMPTY_RESULT:
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "没有更多啦~", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
