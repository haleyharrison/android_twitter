package com.epicodus.twitter_clone.models;

import android.content.Context;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.epicodus.twitter_clone.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Table(name = "Tweets", id = "_id")
public class Tweet {

    @Column(name = "Content")
    private String mContent;

    @Column(name = "CreatedAt")
    private long mCreatedAt;

    @Column(name = "User")
    private User mUser;

    public Tweet() {
        super();
    }

    public Tweet(String content, User user) {
        mContent = content;
        mUser = user;
        mCreatedAt = new Date().getTime();
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getFormattedTime(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat(context.getString(R.string.time_format));
        formatter.setTimeZone(TimeZone.getTimeZone(context.getString(R.string.timezone)));
        return formatter.format(mCreatedAt);
    }
}
