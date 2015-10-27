package com.epicodus.twitter_clone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.twitter_clone.R;
import com.epicodus.twitter_clone.adapters.TweetAdapter;
import com.epicodus.twitter_clone.models.Tweet;
import com.epicodus.twitter_clone.models.User;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private SharedPreferences mPreferences;
    private User mUser;
    private EditText mTweetText;
    private Button mSubmitTweetButton;
    private ArrayList<Tweet> mTweets;
    private TweetAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        if (!isRegistered()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        mTweetText = (EditText) findViewById(R.id.newTweetEdit);
        mSubmitTweetButton = (Button) findViewById(R.id.tweetSubmitButton);
        mTweets = (ArrayList) Tweet.all();
        mAdapter = new TweetAdapter(this, mTweets);
        setListAdapter(mAdapter);

        mSubmitTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = mTweetText.getText().toString();
                if (tweetContent.length() > 140) {
                    int excessCharacters = tweetContent.length() - 140;
                    String warningText = "Oops! Please remove " + excessCharacters + " characters before submitting your tweet!";
                    Toast.makeText(MainActivity.this, warningText, Toast.LENGTH_LONG).show();
                } else {
                    Tweet tweet = new Tweet(tweetContent, mUser);
                    tweet.save();
                    mTweets.add(tweet);
                    mAdapter.notifyDataSetChanged();

                    mTweetText.setText("");
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    private boolean isRegistered() {
        String username = mPreferences.getString("username", null);
        if (username == null) {
            return false;
        } else {
            setUser(username);
            return true;
        }
    }

    private void setUser(String username) {
        User user = User.find(username);
        if (user != null) {
            mUser = user;
        } else {
            mUser = new User(username);
            mUser.save();
        }
        Toast.makeText(this, "Welcome, "+mUser.getName()+"!", Toast.LENGTH_LONG).show();
    }
}
