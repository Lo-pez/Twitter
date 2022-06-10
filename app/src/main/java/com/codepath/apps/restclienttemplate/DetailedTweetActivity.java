package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailedTweetBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailedTweetActivity extends AppCompatActivity {
    private ActivityDetailedTweetBinding binding;
    public static final String TAG = "DetailedTweetActivity";
    ImageView ivProfileImage;
    TextView tvBody;
    Button btnTweet;
    ConstraintLayout clCompose;
    TextView tvScreenName;
    TextView tvTimeStamp;
    TextView etCompose;
    ImageView previewImage;
    FloatingActionButton btnComment;
    FloatingActionButton btnFavorite;
    TextView tvFavoriteCount;
    FloatingActionButton btnRetweet;
    TextView tvRtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_simple.xml -> ActivitySimpleBinding
        binding = ActivityDetailedTweetBinding.inflate(getLayoutInflater());

        Intent intent = getIntent();
        Tweet tweet = Parcels.unwrap(intent.getParcelableExtra("tweet"));

        ivProfileImage = binding.ivProfileImage;
        tvBody = binding.tvBody;
        tvScreenName = binding.tvScreenName;
        previewImage = binding.previewImage;
        tvTimeStamp = binding.tvTimeStamp;
        btnComment = binding.btnComment;
        etCompose = binding.etCompose;
        clCompose = binding.clCompose;
        btnTweet = binding.btnTweet;
        btnFavorite = binding.btnFavorite;
        tvFavoriteCount = binding.tvFavoriteCount;
        btnRetweet = binding.btnRetweet;
        tvRtCount = binding.tvRtCount;

        tvBody.setText(tweet.body);
        tvScreenName.setText(tweet.user.screenName);
        tvTimeStamp.setText(tweet.relativeTimeAgo);
        etCompose.setText("@" + tweet.user.screenName);
        tvFavoriteCount.setText(String.valueOf(tweet.favoriteCount));
        tvRtCount.setText(String.valueOf(tweet.retweetCount));


    }

    public void ViewHolder () {
        ivProfileImage = binding.ivProfileImage;
        tvBody = binding.tvBody;
        tvScreenName = binding.tvScreenName;
        previewImage = binding.previewImage;
        tvTimeStamp = binding.tvTimeStamp;
        btnComment = binding.btnComment;
        etCompose = binding.etCompose;
        clCompose = binding.clCompose;
        btnTweet = binding.btnTweet;
        btnFavorite = binding.btnFavorite;
        tvFavoriteCount = binding.tvFavoriteCount;

    }

    public void bind(Tweet tweet) {
        Glide.with(this).load(tweet.user.profileImageUrl).circleCrop().into(ivProfileImage);
        if (!tweet.media.equals("none")) {
            previewImage.setVisibility(View.VISIBLE);

        }
        Glide.with(DetailedTweetActivity.this).load(tweet.media).into(previewImage);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tweet.isFavorited) {
                    TwitterApp.getRestClient(DetailedTweetActivity.this).favorite(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i(TAG, "Tweet favorited");
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "Tweet failed to favorite" + throwable);
                        }
                    });
                    tweet.isFavorited = true;
                    Drawable newImage = DetailedTweetActivity.this.getDrawable(R.drawable.ic_vector_heart);
                    btnFavorite.setImageDrawable(newImage);
                    tweet.favoriteCount++;

                }
                else {
                    TwitterApp.getRestClient(DetailedTweetActivity.this).unfavorite(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i(TAG, "Tweet unfavorited");
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "Tweet failed to unfavorite" + throwable);
                        }
                    });
                    tweet.isFavorited = false;
                    Drawable newImage = DetailedTweetActivity.this.getDrawable(R.drawable.ic_vector_heart_stroke);
                    btnFavorite.setImageDrawable(newImage);
                    tweet.favoriteCount--;
                }
                tvFavoriteCount.setText(String.valueOf(tweet.favoriteCount));

            }
        });

        btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tweet.isRetweeted) {
                    TwitterApp.getRestClient(DetailedTweetActivity.this).retweet(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i(TAG, "Tweet retweeted");
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "Tweet failed to retweet" + throwable);
                        }
                    });
                    tweet.isRetweeted = true;
                    Drawable newImage = DetailedTweetActivity.this.getDrawable(R.drawable.ic_vector_retweet);
                    btnRetweet.setImageDrawable(newImage);
                    tweet.retweetCount++;

                }
                else {
                    TwitterApp.getRestClient(DetailedTweetActivity.this).unretweet(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i(TAG, "Tweet unretweeted");
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "Tweet failed to unretweet" + throwable);
                        }
                    });
                    tweet.isRetweeted = false;
                    Drawable newImage = DetailedTweetActivity.this.getDrawable(R.drawable.ic_vector_retweet_stroke);
                    btnRetweet.setImageDrawable(newImage);
                    tweet.retweetCount--;
                }
                tvRtCount.setText(String.valueOf(tweet.retweetCount));

            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clCompose.getVisibility() == View.VISIBLE) {
                    clCompose.setVisibility(View.GONE);
                }
                else { clCompose.setVisibility(View.VISIBLE); }
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(DetailedTweetActivity.this, "Sorry, your tweet cannot be empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > ComposeActivity.MAX_TWEET_LENGTH) {
                    Toast.makeText(DetailedTweetActivity.this, "Sorry, your tweet is too long.", Toast.LENGTH_LONG).show();
                    return;
                }
                // Make an API call to Twitter to publish the tweet
                TwitterApp.getRestClient(DetailedTweetActivity.this).publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess published tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            assert tweet != null;
                            Log.i(TAG, "Published tweet says:" + tweet.body);
                            clCompose.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet" + throwable);
                    }
                });
            }
        });
    }
}