package edu.rosehulman.roselabs.sharewithme.LostAndFound;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;
import java.util.Date;

import edu.rosehulman.roselabs.sharewithme.Constants;

public class LostAndFoundPost implements Parcelable {

    private boolean lostFound; // True for Lost, False for Found
    private String title;//
    private String description;//
    private String keywords;//
    private String userId;
    private Date postDate;
    private Date expirationDate;

    @JsonIgnore
    private String key;

    public LostAndFoundPost() {
        this.title = "";
        this.description = "";
        this.keywords = "";
        this.postDate = Calendar.getInstance().getTime();
        this.expirationDate = new Date(Calendar.getInstance().getTimeInMillis() + Constants.DAYS_TO_EXPIRE);
    }

    public LostAndFoundPost(boolean lostFound, String title, String description, String keywords) {
        this.lostFound = lostFound;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.postDate = Calendar.getInstance().getTime();
        this.expirationDate = new Date(Calendar.getInstance().getTimeInMillis() + Constants.DAYS_TO_EXPIRE);
    }

    public boolean isLostFound() {
        return lostFound;
    }

    public void setLostFound(boolean lostFound) {
        this.lostFound = lostFound;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected LostAndFoundPost(Parcel in) {
    }

    public static final Creator<LostAndFoundPost> CREATOR = new Creator<LostAndFoundPost>() {
        @Override
        public LostAndFoundPost createFromParcel(Parcel in) {
            return new LostAndFoundPost(in);
        }

        @Override
        public LostAndFoundPost[] newArray(int size) {
            return new LostAndFoundPost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (lostFound ? 1 : 0));
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(keywords);
        dest.writeString(userId);
        dest.writeString(key);
        dest.writeSerializable(postDate);
        dest.writeSerializable(expirationDate);
    }
}
