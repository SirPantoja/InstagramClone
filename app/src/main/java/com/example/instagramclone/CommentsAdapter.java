package com.example.instagramclone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    public static final String TAG = "CommentsAdapter";
    private Context context;
    private List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment, parent, false);
        Log.i(TAG, "On create");
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        Log.i(TAG, "On bind");
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsernameComment;
        private TextView tvCommentContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Hook up the views
            tvUsernameComment = itemView.findViewById(R.id.tvUsernameComment);
            tvCommentContent = itemView.findViewById(R.id.tvContentComment);
        }

        public void bind(Comment comment) {
            tvUsernameComment.setText(comment.getUser().getUsername() + ":");
            tvCommentContent.setText(comment.getContent());
        }
    }
}
