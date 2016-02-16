package edu.rosehulman.roselabs.sharewithme.BuyAndSell;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;
import java.util.Date;

import edu.rosehulman.roselabs.sharewithme.Constants;

public class BuySellPost implements Parcelable {

    private boolean buy;
    private String title;
    private String description;
    private String keywords;
    private String userId;
    private String price;
    private Date postDate;
    private Date expirationDate;

    @JsonIgnore
    private String key;

    public BuySellPost() {
        this.title = "";
        this.description = "";
        this.keywords = "";
        this.price = "";
        this.postDate = Calendar.getInstance().getTime();
        this.expirationDate = new Date(Calendar.getInstance().getTimeInMillis() + Constants.DAYS_TO_EXPIRE);
    }

    public BuySellPost(boolean buy, String title, String description, String keywords, String price) {
        this.buy = buy;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.price = price;
        this.postDate = Calendar.getInstance().getTime();
        this.expirationDate = new Date(Calendar.getInstance().getTimeInMillis() + Constants.DAYS_TO_EXPIRE);
    }

    //Temporary constructor just for testing purposes
    public BuySellPost(String title, String desc, boolean buy) {
        this.title = title;
        this.description = desc;
        this.buy = buy;
    }

    protected BuySellPost(Parcel in) {
        buy = in.readByte() != 0;
        title = in.readString();
        description = in.readString();
        keywords = in.readString();
        userId = in.readString();
        price = in.readString();
        key = in.readString();
        postDate = (java.util.Date) in.readSerializable();
        expirationDate = (java.util.Date) in.readSerializable();
    }

    public static final Creator<BuySellPost> CREATOR = new Creator<BuySellPost>() {
        @Override
        public BuySellPost createFromParcel(Parcel in) {
            return new BuySellPost(in);
        }

        @Override
        public BuySellPost[] newArray(int size) {
            return new BuySellPost[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (buy ? 1 : 0));
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(keywords);
        dest.writeString(userId);
        dest.writeString(price);
        dest.writeString(key);
        dest.writeSerializable(postDate);
        dest.writeSerializable(expirationDate);
    }
}