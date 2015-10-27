package com.epicodus.twitter_clone.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users", id = "_id")
public class User extends Model {

    @Column(name = "Name")
    private String mName;

    public User() {
        super();
    }

    public User(String name) {
        super();
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
