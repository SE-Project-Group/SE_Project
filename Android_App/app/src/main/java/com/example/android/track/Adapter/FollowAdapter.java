package com.example.android.track.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.track.Activity.PersonalHomeActivity;
import com.example.android.track.Application.MyApplication;
import com.example.android.track.Model.Follow;
import com.example.android.track.R;
import com.example.android.track.Util.UserRequester;
import com.example.android.track.Util.Verify;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by jarvis on 2017/7/6.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder>{
    private List<Follow> mFollowList;
    private Context context;

    //message
    private final static int FOLLOW_ACTION_FAILED = 5;
    private final static int CANCEL_FOLLOW_SUCCESS = 6;
    private final static int ADD_FOLLOW_SUCCESS = 7;
    private final static int ALSO_FOLLOW_SUCCESS = 8;
    private final static int CANCEL_FRIEND_SUCCESS = 9;

    // requester
    private UserRequester requester = new UserRequester();

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView portrait;
        TextView user_name;
        Button add_follow_btn;
        Button also_follow_btn;
        Button cancel_follow_btn;
        Button cancel_friend_btn;

        public ViewHolder(View view){
            super(view);
            portrait = (CircleImageView) view.findViewById(R.id.portrait);
            user_name = (TextView)view.findViewById(R.id.user_name_text);
            add_follow_btn = (Button) view.findViewById(R.id.add_follow_btn);
            cancel_follow_btn = (Button) view.findViewById(R.id.cancel_follow_btn);
            also_follow_btn = (Button) view.findViewById(R.id.also_follow_btn);
            cancel_friend_btn = (Button) view.findViewById(R.id.cancel_friend_btn);
        }
    }

    public FollowAdapter(List<Follow> followList, Context cxt){
        mFollowList = followList;
        context = cxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_follow,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.add_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verify verify = new Verify();
                if(!verify.getLoged()) {
                    Toast.makeText(MyApplication.getContext(), "not log in", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.add_follow_btn.setVisibility(View.GONE);
                holder.cancel_follow_btn.setVisibility(View.VISIBLE);
                int position = holder.getAdapterPosition();
                Follow follow = mFollowList.get(position);
                follow(follow.getUser_id());
            }
        });

        holder.also_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.also_follow_btn.setVisibility(View.GONE);
                holder.cancel_friend_btn.setVisibility(View.VISIBLE);
                int position = holder.getAdapterPosition();
                Follow follow = mFollowList.get(position);
                follow(follow.getUser_id());
            }
        });

        holder.cancel_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cancel_follow_btn.setVisibility(View.GONE);
                holder.add_follow_btn.setVisibility(View.VISIBLE);
                int position = holder.getAdapterPosition();
                Follow follow = mFollowList.get(position);
                cancelFollow(follow.getUser_id());
            }
        });

        holder.cancel_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cancel_friend_btn.setVisibility(View.GONE);
                holder.also_follow_btn.setVisibility(View.VISIBLE);
                int position = holder.getAdapterPosition();
                Follow follow = mFollowList.get(position);
                cancelFollow(follow.getUser_id());
            }
        });

        holder.portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Follow follow = mFollowList.get(position);
                int user_id = follow.getUser_id();
                Intent intent = new Intent(context, PersonalHomeActivity.class);
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);
            }
        });

        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Follow follow = mFollowList.get(position);
                int user_id = follow.getUser_id();
                Intent intent = new Intent(context, PersonalHomeActivity.class);
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Follow user = mFollowList.get(position);
        holder.user_name.setText(user.getUser_name());
        String state = user.getState();
        if(state.equals("following"))
            holder.cancel_follow_btn.setVisibility(View.VISIBLE);
        if(state.equals("follower"))
            holder.also_follow_btn.setVisibility(View.VISIBLE);
        if(state.equals("friend"))
            holder.cancel_friend_btn.setVisibility(View.VISIBLE);
        if(state.equals("stranger"))
            holder.add_follow_btn.setVisibility(View.VISIBLE);

        // load the picture use glide
        Glide.with(context)
                .load(user.getportrait_url())
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.default_portrait)
                .into(holder.portrait);

    }

    @Override
    public int getItemCount(){
        return mFollowList.size();
    }

    private void follow(final int who){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = requester.follow(who);
            }
        }).start();
    }

    private void cancelFollow(final int who){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = requester.cancel_follow(who);
            }
        }).start();
    }
}
