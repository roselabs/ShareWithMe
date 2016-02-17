package edu.rosehulman.roselabs.sharewithme.Dashboard;

import java.util.Date;

/**
 * Created by josebaf on 2/15/2016.
 */
public class DashboardPost implements Comparable<DashboardPost>{

    private String title;
    private String userId;
    private String category;
    private Date postDate;
    private String key;

    public DashboardPost() {
        this.title = "";
        this.userId = "";
        this.category = "";
        this.key = "";
        this.postDate = new Date(1);
    }

    public DashboardPost(String title, String userId, String category, String key, Date postDate) {
        this.title = title;
        this.userId = userId;
        this.category = category;
        this.key = key;
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public int compareTo(DashboardPost another) {
        return another.postDate.compareTo(this.postDate);
    }
}
