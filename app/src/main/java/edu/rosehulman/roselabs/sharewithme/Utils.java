package edu.rosehulman.roselabs.sharewithme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.urbanairship.UAirship;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.rosehulman.roselabs.sharewithme.PushNotification.SendNotificationTask;

public class Utils {

    public static void sendNotification(String commentUserId, String mPostAuthorUserId, String postKey, String category){
        if (commentUserId.equals(mPostAuthorUserId)) return;
        SendNotificationTask task = new SendNotificationTask();
        JSONObject json = new JSONObject();
        List<String> devices_types = new ArrayList<>();
        devices_types.add("android");

        try {
            json.put("audience", new JSONObject().put("named_user", mPostAuthorUserId));
            json.put("device_types",  new JSONArray(devices_types));
            String deepLinkContent = "sharewithme://deeplink/categories/posts?postKey=" + postKey + "&category=" + category;
            json.put("notification", new JSONObject()
                    .put("alert", "@" + commentUserId + " commented your post")
                    .put("actions", new JSONObject()
                            .put("open", new JSONObject()
                                    .put("type", "deep_link")
                                    .put("content", deepLinkContent))));
        } catch (JSONException e){
            Log.d("BILADA", e.toString());
        }
        task.execute("api/push", json.toString());
    }

    public static void associateUser(){
        String channelId = UAirship.shared().getPushManager().getChannelId();
        SendNotificationTask task = new SendNotificationTask();
        JSONObject json = new JSONObject();
        try {
            json.put("channel_id", channelId);
            json.put("device_type", "android");
            json.put("named_user_id", new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        } catch (JSONException e){
            Log.d("BILADA", e.toString());
        }
        task.execute("api/named_users/associate", json.toString());
    }

    public static void disassociateUser(){
        String channelId = UAirship.shared().getPushManager().getChannelId();
        SendNotificationTask task = new SendNotificationTask();
        JSONObject json = new JSONObject();
        try {
            json.put("channel_id", channelId);
            json.put("device_type", "android");
            json.put("named_user_id", new Firebase(Constants.FIREBASE_URL).getAuth().getUid());
        } catch (JSONException e){
            Log.d("BILADA", e.toString());
        }
        task.execute("api/named_users/disassociate", json.toString());
    }

    public static String encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        byte[] byteArray = bYtE.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap decodeStringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String formatPrice(EditText price) {
        String formattedPrice = price.getText().toString();

        if (formattedPrice.isEmpty())
            return formattedPrice;

        if (formattedPrice.contains(".")) {
            int decimal = formattedPrice.indexOf(".");

            if (formattedPrice.length() - 1 < decimal + 2) {
                for (int i = formattedPrice.length() - 1; i < decimal + 2; i++)
                    formattedPrice += 0;
            } else if (formattedPrice.length() - 1 > decimal + 2) {
                formattedPrice = formattedPrice.substring(0, decimal + 3);
            }
        } else {
            formattedPrice += ".00";
        }

        return formattedPrice;
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "";
        }

        String phone;

        //phone: +1 (999) 999-9999

        phone = "phone: +";
        phone = phone + phoneNumber.substring(0, 1);
        phone = phone + " (";
        phone = phone + phoneNumber.substring(1, 4);
        phone = phone + ") ";
        phone = phone + phoneNumber.substring(4, 7);
        phone = phone + "-";
        phone = phone + phoneNumber.substring(7);

        return phone;
    }

    public static String getStringDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        return dateFormat.format(date);
    }

}
