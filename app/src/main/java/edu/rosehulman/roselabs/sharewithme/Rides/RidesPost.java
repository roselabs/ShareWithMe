package edu.rosehulman.roselabs.sharewithme.Rides;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thais Faria on 1/29/2016.
 */
public class RidesPost {

    private int postId;
    private boolean offer;//
    private String title;//
    private String description;//
    private String keywords;//
    private String userId;
    private String price;//
    private String departureLocal;//
    private String destinationLocal;//
    private Date rideDate;//
    private Date postDate;
    private Date expirationDate;

    @JsonIgnore
    private String key;

    public RidesPost() {}

    public RidesPost(int postId, boolean offer, String title, String description,
                     String keywords, String userId, String price, String departureLocal,
                     String destinationLocal, Date rideDate){
        this.postId = postId;
        this.offer = offer;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.userId = userId;
        this.price = price;
        this.departureLocal = departureLocal;
        this.destinationLocal = destinationLocal;
        this.rideDate = rideDate;
        this.postDate = Calendar.getInstance().getTime();
    }

    public RidesPost(String title, String description, boolean offer){
        this.title = title;
        this.description = description;
        this.offer = offer;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

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

}
