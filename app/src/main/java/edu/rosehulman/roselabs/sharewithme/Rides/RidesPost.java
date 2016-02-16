package edu.rosehulman.roselabs.sharewithme.Rides;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;
import java.util.Date;

import edu.rosehulman.roselabs.sharewithme.Constants;

public class RidesPost implements Parcelable {

    private boolean offer;
    private String title;
    private String description;
    private String keywords;
    private String userId;
    private String price;
    private String departureLocal;
    private String destinationLocal;
    private Date rideDate;
    private Date postDate;
    private Date expirationDate;

    @JsonIgnore
    private String key;

    public RidesPost() {
        this.title = "";
        this.description = "";
        this.keywords = "";
        this.price = "";
        this.departureLocal = "";
        this.destinationLocal = "";
        this.postDate = Calendar.getInstance().getTime();
        this.expirationDate = new Date(Calendar.getInstance().getTimeInMillis() + Constants.DAYS_TO_EXPIRE);
    }

    public RidesPost(boolean offer, String price, String title, String departureLocal,
                     Date rideDate, String destinationLocal, String description,
                     String keywords) {
        this.offer = offer;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.price = price;
        this.departureLocal = departureLocal;
        this.destinationLocal = destinationLocal;
        this.rideDate = rideDate;
        this.postDate = Calendar.getInstance().getTime();
        this.expirationDate = new Date(Calendar.getInstance().getTimeInMillis() + Constants.DAYS_TO_EXPIRE);
    }

    public RidesPost(String title, String description, boolean offer){
        this.title = title;
        this.description = description;
        this.offer = offer;
    }

    @JsonIgnore
    protected RidesPost(Parcel in) {
        offer = in.readByte() != 0;
        title = in.readString();
        description = in.readString();
        keywords = in.readString();
        userId = in.readString();
        price = in.readString();
        departureLocal = in.readString();
        destinationLocal = in.readString();
        key = in.readString();
        rideDate = (java.util.Date) in.readSerializable();
        postDate = (java.util.Date) in.readSerializable();
        expirationDate = (java.util.Date) in.readSerializable();
    }

    @JsonIgnore
    public static final Creator<RidesPost> CREATOR = new Creator<RidesPost>() {
        @Override
        public RidesPost createFromParcel(Parcel in) {
            return new RidesPost(in);
        }

        @Override
        public RidesPost[] newArray(int size) {
            return new RidesPost[size];
        }
    };

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
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

    public String getDepartureLocal() {
        return departureLocal;
    }

    public void setDepartureLocal(String departureLocal) {
        this.departureLocal = departureLocal;
    }

    public String getDestinationLocal() {
        return destinationLocal;
    }

    public void setDestinationLocal(String destinationLocal) {
        this.destinationLocal = destinationLocal;
    }

    public Date getRideDate() {
        return rideDate;
    }

    public void setRideDate(Date rideDate) {
        this.rideDate = rideDate;
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

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (offer ? 1 : 0));
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(keywords);
        dest.writeString(userId);
        dest.writeString(price);
        dest.writeString(departureLocal);
        dest.writeString(destinationLocal);
        dest.writeString(key);
        dest.writeSerializable(rideDate);
        dest.writeSerializable(postDate);
        dest.writeSerializable(expirationDate);
    }
}
