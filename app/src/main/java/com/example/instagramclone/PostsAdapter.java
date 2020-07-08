package com.example.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public static final String TAG = "PostsAdapter";
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private TextView tvPostDescription;
        private ImageView ivPostImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
        }

        public void bind(final Post post) {
            // Bind the post data to the view elements
            tvPostDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            Glide.with(context).load(post.getImage().getUrl()).into(ivPostImage);

            // Set on click listener to the image to go to details
            ivPostImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, post.getUser().getUsername() + "'s post was clicked");
                    // Use an intent to send over the post details and start the new activity
                    Intent intent = new Intent(context, PostDetailsActivity.class);
                    intent.putExtra("username", post.getUser().getUsername());
                    intent.putExtra("description", post.getDescription());
                    intent.putExtra("image", post.getImage().getUrl());
                    intent.putExtra("createdAt", post.getCreatedAt().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    // Clear the list of posts
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of posts
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
