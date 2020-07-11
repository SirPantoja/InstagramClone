package com.example.instagramclone;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_PTR_USER = "ptrUser";
    public static final String KEY_PTR_POST = "ptrPost";
    public static final String KEY_CONTENT = "content";

    // Required empty constructor
    public Comment() { }

    public String getContent() {
        return getString(KEY_CONTENT);
    }

    public void setContent(String content) {
        put(KEY_CONTENT, content);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_PTR_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_PTR_USER, user);
    }
}
