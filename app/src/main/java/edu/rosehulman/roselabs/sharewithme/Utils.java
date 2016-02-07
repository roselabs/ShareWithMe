package edu.rosehulman.roselabs.sharewithme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rodrigr1 on 2/6/2016.
 */
public class Utils {

    public static String encodeBitmap(Bitmap bitmap){
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        byte[] byteArray = bYtE.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap decodeStringToBitmap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static String formatPrice(EditText price){
        String formattedPrice = price.getText().toString();

        if(formattedPrice.isEmpty())
            return formattedPrice;

        if(formattedPrice.contains(".")) {
            int decimal = formattedPrice.indexOf(".");

            if (formattedPrice.length() - 1 < decimal + 2) {
                for (int i = formattedPrice.length() - 1; i < decimal + 2; i++)
                    formattedPrice += 0;
            } else if (formattedPrice.length() - 1 > decimal + 2) {
                formattedPrice = formattedPrice.substring(0, decimal + 3);
            }
        }else{
            formattedPrice += ".00";
        }

        return formattedPrice;
    }

    public static String formatDateFromPicker(DatePicker date){
        String formattedDate = date.getYear() + "/" + (date.getMonth()+1) + "/" + date.getDayOfMonth();

        return formattedDate;
    }

    public static String formatDateToAmerican(String databaseFormat){
        String[] dateArray = databaseFormat.split("/");

        String formattedDate = dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0];

        return formattedDate;
    }

    public static String formatPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){
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

    public static String getStringDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        return dateFormat.format(date);
    }

}
