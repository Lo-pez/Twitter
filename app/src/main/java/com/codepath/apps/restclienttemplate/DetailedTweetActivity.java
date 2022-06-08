package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class DetailedTweetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tweet);

    }

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimeStamp;
        TextView tvHour;
        TextView tvDate;
        TextView tvRetweetCount;
        TextView tvQuoteTweetCount;
        TextView tvLikesCount;
        TextView tvTwitterAndroid;
        Button btnRetweet;
        Button btnLike;
        Button btnShare;
        Button btnComment;
        Button btnTweet;
        ImageView previewImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            previewImage = itemView.findViewById(R.id.previewImage);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            btnTweet = itemView.findViewById(R.id.btnTweet);
            btnComment = itemView.findViewById(R.id.btnComment);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnRetweet = itemView.findViewById(R.id.btnRetweet);
            btnShare = itemView.findViewById(R.id.btnShare);
            tvTwitterAndroid = itemView.findViewById(R.id.tvTwitterAndroid);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            tvQuoteTweetCount = itemView.findViewById(R.id.tvQuoteTweetCount);
            tvRetweetCount = itemView.findViewById(R.id.tvRetweetCount);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvDate.setText(tweet.createdAt);
            Glide.with(DetailedTweetActivity.this).load(tweet.user.profileImageUrl).into(ivProfileImage);
            if (tweet.media != "none")
                previewImage.setVisibility(View.VISIBLE);
            Glide.with(DetailedTweetActivity.this).load(tweet.media).into(previewImage);
        }
    }

}