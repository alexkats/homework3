package ru.ifmo.android.homework3;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author Alexey Katsman
 * @since 30.11.16
 */

public class ImageService extends IntentService {

    private static final String TAG = ImageService.class.getSimpleName();

    public ImageService() {
        super("ImageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            File file = new File(getFilesDir(), "image.jpg");

            if (file.exists()) {
                file.delete();
            }

            URL url = new URL(intent.getStringExtra(ImageServiceBroadcastReceiver.getImageUrlExtraName()));
            OutputStream fileOutputStream = openFileOutput("image.jpg", Context.MODE_PRIVATE);
            InputStream inputStream = url.openConnection().getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            inputStream.close();
            fileOutputStream.close();
            sendBroadcast(new Intent(ImageActivity.getBroadcastName()));
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
