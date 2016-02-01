package edu.rosehulman.roselabs.sharewithme.Comments;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Comment {

    @JsonIgnore
    private String key;
    private String content;
    private String userId;
    private Date date;
    private String postKey;

    public Comment() {
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
