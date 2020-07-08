package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PostDetailsActivity extends AppCompatActivity {

    public static final String TAG = "PostDetailsActivity";
    private TextView tvUsernameDetails;
    private TextView tvDescriptionDetails;
    private TextView tvTimeStampDetails;
    private ImageView ivPostDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Log.i(TAG, "Creating the Post Details Activity");

        // Link up the views
        tvUsernameDetails = findViewById(R.id.tvUsernameDetails);
        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);
        tvTimeStampDetails = findViewById(R.id.tvTimeStampDetails);
        ivPostDetails = findViewById(R.id.ivPostDetails);

        // Get the intent with the data
        Intent intent = getIntent();

        // Set the values for each text view
        tvUsernameDetails.setText(intent.getStringExtra("username"));
        tvDescriptionDetails.setText(intent.getStringExtra("description"));
        tvTimeStampDetails.setText("Post created on " + intent.getStringExtra("createdAt"));

        // Use glide for the image
        Glide.with(this).load(intent.getStringExtra("image")).into(ivPostDetails);
    }
}