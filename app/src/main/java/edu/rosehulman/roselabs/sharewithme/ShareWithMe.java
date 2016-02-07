package edu.rosehulman.roselabs.sharewithme;

import android.app.Application;

import com.urbanairship.UAirship;

/**
 * Created by rodrigr1 on 2/6/2016.
 */
public class ShareWithMe extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        UAirship.takeOff(this, new UAirship.OnReadyCallback() {
            @Override
            public void onAirshipReady(UAirship airship) {

                // Enable user notifications
                airship.getPushManager().setUserNotificationsEnabled(true);
            }
        });
    }
}
