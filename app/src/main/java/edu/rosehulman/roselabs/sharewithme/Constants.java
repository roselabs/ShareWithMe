package edu.rosehulman.roselabs.sharewithme;

public class Constants {
    public static final String FIREBASE_URL = "https://sharewithme.firebaseio.com/";
    public static final String FIREBASE_DRAFT_URL = FIREBASE_URL + "drafts/";

    public static final String FIREBASE_RIDES_POST_URL = FIREBASE_URL + "categories/Rides/posts/";
    public static final String FIREBASE_RIDES_DRAFT_URL = FIREBASE_DRAFT_URL + "categories/Rides/posts/";

    public static final String FIREBASE_BUY_SELL_POST_URL = FIREBASE_URL + "categories/BuyAndSell/posts/";
    public static final String FIREBASE_BUY_SELL_DRAFT_URL = FIREBASE_DRAFT_URL + "categories/BuyAndSell/posts/";


    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int CROP_IMAGE_REQUEST = 2;
}
