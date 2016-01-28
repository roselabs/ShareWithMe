package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

import java.util.Calendar;
import java.util.Date;

public class BuySellPost {

    private int postId;
    private boolean buy;
    private String title;
    private String description;
    private String keywords;
    private String userId;
    private String price;
    private Date postDate;
    private Date expirationDate;

    public BuySellPost(int postId, boolean buy, String title, String description, String keywords, String userId, String price) {
        this.postId = postId;
        this.buy = buy;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.userId = userId;
        this.price = price;
        this.postDate = Calendar.getInstance().getTime();
    }

    //Temporary constructor just for testing purposes
    public BuySellPost(String title, String desc, boolean buy){
        this.title = title;
        this.description = desc;
        this.buy = buy;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}