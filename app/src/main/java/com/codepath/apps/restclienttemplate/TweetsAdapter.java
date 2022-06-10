package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityTimelineActivityBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;
    TwitterClient client;
    ActivityTimelineActivityBinding binding;
    public static final String TAG = "TweetsAdapter";



    // Pass in context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        client = TwitterApp.getRestClient(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        // Set click listener on button

        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get data at position
        Tweet tweet = tweets.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedTweetActivity.class);
                intent.putExtra("tweet", Parcels.wrap(tweets.get(holder.getAdapterPosition())));
                context.startActivity(intent);
                Log.i("TAG", "Tweet has been clicked");
            }
        });

        // Bind the tweet with the viewholder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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


        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            previewImage = itemView.findViewById(R.id.previewImage);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            btnComment = itemView.findViewById(R.id.btnComment);
            etCompose = itemView.findViewById(R.id.etCompose);
            clCompose = itemView.findViewById(R.id.clCompose);
            btnTweet = itemView.findViewById(R.id.btnTweet);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            tvFavoriteCount = itemView.findViewById(R.id.tvFavoriteCount);
            btnRetweet = itemView.findViewById(R.id.btnRetweet);
            tvRtCount = itemView.findViewById(R.id.tvRtCount);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvTimeStamp.setText(tweet.relativeTimeAgo);
            etCompose.setText("@" + tweet.user.screenName);
            tvFavoriteCount.setText(String.valueOf(tweet.favoriteCount));
            tvRtCount.setText(String.valueOf(tweet.retweetCount));

            Glide.with(context).load(tweet.user.profileImageUrl).circleCrop().into(ivProfileImage);
            if (!tweet.media.equals("none")) {
                previewImage.setVisibility(View.VISIBLE);

            }
            Glide.with(context).load(tweet.media).into(previewImage);
            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!tweet.isFavorited) {
                        TwitterApp.getRestClient(context).favorite(tweet.id, new JsonHttpResponseHandler() {
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
                        Drawable newImage = context.getDrawable(R.drawable.ic_vector_heart);
                        btnFavorite.setImageDrawable(newImage);
                        tweet.favoriteCount++;

                    }
                    else {
                        TwitterApp.getRestClient(context).unfavorite(tweet.id, new JsonHttpResponseHandler() {
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
                        Drawable newImage = context.getDrawable(R.drawable.ic_vector_heart_stroke);
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
                        TwitterApp.getRestClient(context).retweet(tweet.id, new JsonHttpResponseHandler() {
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
                        Drawable newImage = context.getDrawable(R.drawable.ic_vector_retweet);
                        btnRetweet.setImageDrawable(newImage);
                        tweet.retweetCount++;

                    }
                    else {
                        TwitterApp.getRestClient(context).unretweet(tweet.id, new JsonHttpResponseHandler() {
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
                        Drawable newImage = context.getDrawable(R.drawable.ic_vector_retweet_stroke);
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
                        Toast.makeText(context, "Sorry, your tweet cannot be empty.",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (tweetContent.length() > ComposeActivity.MAX_TWEET_LENGTH) {
                        Toast.makeText(context, "Sorry, your tweet is too long.",Toast.LENGTH_LONG).show();
                        return;
                    }
                    // Make an API call to Twitter to publish the tweet
                    client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
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

        @Override
        public void onClick(View view) {

        }
    }
}
