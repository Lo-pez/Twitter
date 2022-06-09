package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailedTweetBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailedTweetActivity extends AppCompatActivity {
    private ActivityDetailedTweetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_simple.xml -> ActivitySimpleBinding
        binding = ActivityDetailedTweetBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);
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
        FloatingActionButton btnRetweet;
        FloatingActionButton btnLike;
        FloatingActionButton btnShare;
        FloatingActionButton btnComment;
        Button btnTweet;
        ImageView previewImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = binding.ivProfileImage;
            tvBody = binding.tvBody;
            tvScreenName = binding.tvScreenName;
            previewImage = binding.previewImage;
            tvTimeStamp = binding.tvTimeStamp;
            btnComment = binding.btnComment;
            btnLike = binding.btnLike;
            btnRetweet = binding.btnRetweet;
            btnShare = binding.btnShare;
            tvTwitterAndroid = binding.tvTwitterAndroid;
            tvLikesCount = binding.tvLikesCount;
            tvQuoteTweetCount = binding.tvQuoteTweetCount;
            tvRetweetCount = binding.tvRetweetCount;
            tvHour = binding.tvHour;
            tvDate = binding.tvDate;
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvDate.setText(tweet.createdAt);
            Glide.with(DetailedTweetActivity.this).load(tweet.user.profileImageUrl).circleCrop().into(ivProfileImage);
            if (tweet.media != "none")
                previewImage.setVisibility(View.VISIBLE);
            Glide.with(DetailedTweetActivity.this).load(tweet.media).into(previewImage);
        }
    }

}