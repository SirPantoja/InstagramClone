package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    public static final String TAG = "PostDetailsActivity";
    private TextView tvUsernameDetails;
    private TextView tvDescriptionDetails;
    private TextView tvTimeStampDetails;
    private TextView tvComment;
    private Button btnCommentSubmit;
    private ImageView ivPostDetails;
    private RecyclerView rvComments;
    protected List<Comment> comments;
    private CommentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Log.i(TAG, "Creating the Post Details Activity");

        // Get the intent with the data
        final Intent intent = getIntent();

        // Link up the views
        tvUsernameDetails = findViewById(R.id.tvUsernameDetails);
        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);
        tvTimeStampDetails = findViewById(R.id.tvTimeStampDetails);
        ivPostDetails = findViewById(R.id.ivPostDetails);
        rvComments = findViewById(R.id.rvComments);
        tvComment = findViewById(R.id.tvComment);
        btnCommentSubmit = findViewById(R.id.btnCommentSubmit);
        Post post = Parcels.unwrap(intent.getParcelableExtra("post"));
        Log.i(TAG, post.getUser().getUsername());

        // Set up the on click listener for the comment submit button
        btnCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Button clicked");
                // Make sure the comment is not empty
                if (tvComment.getText().toString().isEmpty()) {
                    Toast.makeText(PostDetailsActivity.this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment newComment = new Comment();
                newComment.setContent(tvComment.getText().toString());
                newComment.setUser(ParseUser.getCurrentUser());
                queryPost(newComment, intent.getStringExtra("id"));

                tvComment.setText("");
                /*newComment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.e(TAG, "Error while saving comment", e);
                        Toast.makeText(PostDetailsActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
                    }
                });*/
                Log.i(TAG, "Comment saved");
            }
        });

        // Set up the recycler view for comments
        comments = new ArrayList<>();
        adapter = new CommentsAdapter(this, comments);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        queryComments(post);

        // Set the values for each text view
        tvUsernameDetails.setText(intent.getStringExtra("username"));
        tvDescriptionDetails.setText(intent.getStringExtra("description"));
        tvTimeStampDetails.setText("Post created on " + intent.getStringExtra("createdAt"));

        // Use glide for the image
        Glide.with(this).load(intent.getStringExtra("image")).into(ivPostDetails);
    }

    private void queryPost(final Comment comment, String postId) {
        Log.i(TAG, "Post query called" + postId);
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(1);
        query.whereEqualTo("objectId", postId);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting single post");
                }
                comment.put(Comment.KEY_PTR_POST, objects.get(0));
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                            Log.e(TAG, "Error while saving comment", e);
                            return;
                        }
                    }
                });
                return;
            }
        });
    }

    private void queryComments(Post post) {
        Log.i(TAG, "Comment query called, id: ");
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_PTR_POST);
        query.setLimit(20);
        query.whereEqualTo(Comment.KEY_PTR_POST, post);
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> newComments, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting comments");
                    return;
                }
                Log.i(TAG, newComments.toString());
                for (Comment newComment : newComments) {
                    try {
                        Log.i(TAG, "Comment: " + newComment.getContent() + ", Username: " + newComment.getUser().fetchIfNeeded().getUsername());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                comments.addAll(newComments);
                adapter.notifyDataSetChanged();
            }
        });
    }
}