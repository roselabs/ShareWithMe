package edu.rosehulman.roselabs.sharewithme;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigr1 on 2/7/2016.
 */
public class SendNotificationTask extends AsyncTask<String, Void, String> {

    public SendNotificationTask(){

    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection;
        OutputStreamWriter request;

        URL url;
        String response;
        String message = params[0];

        try {
            String authString = "Yaw5g-YYRuSfSmzRHug9lw:Udh8o5LhScuPq8J90GNLLg";
            byte[] str = Base64.encode(authString.getBytes(), 64);
            String authS = new String(str);

            url = new URL("https://go.urbanairship.com/api/push");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + authS);
            connection.setRequestProperty("Accept", "application/vnd.urbanairship+json; version=3;");
            connection.setRequestMethod("POST");
            request = new OutputStreamWriter(connection.getOutputStream());
            request.write("{\"audience\": \"all\", \"notification\": {\"alert\": \" " + message + " \"},\"device_types\": \"all\"}");
            request.flush();
            request.close();

            int responseCode = connection.getResponseCode();

            Log.d("BILADA", "Response code: " + responseCode);

            String line;
            InputStreamReader isr;
            if (responseCode >= 400){
                isr = new InputStreamReader(connection.getErrorStream());
            } else {
                isr = new InputStreamReader(connection.getInputStream());
            }
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
            Log.d("BILADA", response);
            isr.close();
            reader.close();
            return response;
        } catch(IOException e) {
            e.printStackTrace();
            Log.d("BILADA", "ha");
        }
        return "{}";
    }

}
