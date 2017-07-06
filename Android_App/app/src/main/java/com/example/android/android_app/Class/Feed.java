package com.example.android.android_app.Class;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by thor on 2017/6/28.
 */

public class Feed implements Serializable{
    private String _id;
    private long user_ID;
    private String feed_owner;
    private String text;
    private String date;
    private int like_cnt;
    private int share_cnt;
    private int comment_cnt;
    private List<Integer> pic_id_list;
    private String position;
    private double latitude;
    private double longitude;

    // get around
    public Feed(long user_ID, String feed_owner, String text, String date, int like_cnt, int share_cnt, int comment_cnt, List<Integer> pic_id_list, String position, double latitude, double longitude) {
        this.user_ID = user_ID;
        this.feed_owner = feed_owner;
        this.text = text;
        this.date = date;
        this.like_cnt = like_cnt;
        this.share_cnt = share_cnt;
        this.comment_cnt = comment_cnt;
        this.pic_id_list = pic_id_list;
        this.position = position;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // discover fragment


    public Feed(String feed_owner, String text, String date, int like_cnt, int share_cnt, int comment_cnt,List<Integer> pic_id_list, long user_ID) {
        this.feed_owner = feed_owner;
        this.text = text;
        this.date = date;
        this.like_cnt = like_cnt;
        this.share_cnt = share_cnt;
        this.comment_cnt = comment_cnt;
        this.pic_id_list= pic_id_list;
        this.user_ID = user_ID;
    }



    public double getLatitude(){
        return latitude;
    }
    public double getlongitude(){
        return longitude;
    }

    public String getFeed_owner() {
        return feed_owner;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public int getShare_cnt() {
        return share_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public List<Integer> getPic_id_list() {
        return pic_id_list;
    }

    public String getPosition(){
        return position;
    }

    public long getUser_ID(){
        return user_ID;
    }


    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUser_ID(long user_ID) {
        this.user_ID = user_ID;
    }

    public void setFeed_owner(String feed_owner) {
        this.feed_owner = feed_owner;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public void setShare_cnt(int share_cnt) {
        this.share_cnt = share_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    public void setPic_id_list(List<Integer> pic_id_list) {
        this.pic_id_list = pic_id_list;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}