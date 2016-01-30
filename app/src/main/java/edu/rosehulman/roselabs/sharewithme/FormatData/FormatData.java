package edu.rosehulman.roselabs.sharewithme.FormatData;

/**
 * Created by Thais Faria on 1/30/2016.
 */
public class FormatData {

    public static String formatPrice(String price){
        String formattedPrice = price;

        if(formattedPrice.contains(".")){
            int decimal = formattedPrice.indexOf(".");

            if(formattedPrice.length() -1 < decimal + 2){
                for(int i = formattedPrice.length() -1; i < decimal + 2; i++){
                    formattedPrice += 0;
                }
            }else if (formattedPrice.length() - 1 > decimal + 2) {
                formattedPrice = formattedPrice.substring(0, decimal + 3);
            }
            //else do nothing
        }else{
            formattedPrice += ".00";
        }

        return formattedPrice;
    }
}
