package edu.rosehulman.roselabs.sharewithme;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.urbanairship.util.UriUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseDeepLinkActivity extends Activity {

    public static final String POST_DEEP_LINK = "/categories/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null || intent.getData() == null) {
            finish();
        }

        openDeepLink(intent.getData());

        // Finish this activity
        finish();
    }

    private void openDeepLink(Uri deepLink) {
        String path = deepLink.getPath();
        Intent intent = new Intent(this, MainActivity.class);

        Bundle extras = parseOptions(deepLink);
        if (extras != null) {
            // Add the extras to the intent
            intent.putExtras(extras);
        }

        if (POST_DEEP_LINK.equals(path)) {
            // Launch preferences
            startActivity(intent);
        } else {
            // Fall back to the main activity
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private Bundle parseOptions(Uri deepLink) {
        Bundle options = new Bundle();
        Map<String, List<String>> queryParameters = UriUtils.getQueryParameters(deepLink);

        if (queryParameters == null) {
            return options;
        }

        for (String key : queryParameters.keySet()) {
            List<String> values = queryParameters.get(key);
            if (values.size() == 1) {
                options.putString(key, values.get(0));
            } else if (values.size() > 1) {
                options.putStringArrayList(key, new ArrayList<>(values));
            }
        }

        return options;
    }
}
