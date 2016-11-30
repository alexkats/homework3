package ru.ifmo.android.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Alexey Katsman
 * @since 30.11.16
 */

public class ImageServiceBroadcastReceiver extends BroadcastReceiver {

    public static String getImageUrlExtraName() {
        return "IMAGE_URL_EXTRA";
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ImageService.class);
        serviceIntent.putExtra(getImageUrlExtraName(), ImageActivity.getRandomImageUrl());
        context.startService(serviceIntent);
    }
}
