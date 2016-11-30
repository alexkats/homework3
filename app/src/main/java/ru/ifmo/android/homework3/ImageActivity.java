package ru.ifmo.android.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageActivity extends AppCompatActivity {

    private static final List<String> IMAGE_URLS = new ArrayList<>();

    private View textView;
    private ImageView imageView;
    private BroadcastReceiver imageReceiver;

    static {
        IMAGE_URLS.add("http://gallery.photo.net/photo/7243244-md.jpg");
        IMAGE_URLS.add("http://gallery.photo.net/photo/10993690-md.jpg");
        IMAGE_URLS.add("http://gallery.photo.net/photo/10256012-md.jpg");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initialize();
    }

    private void initialize() {
        findViews();
        findAndSetImage();
        setupReceiver();
    }

    private void findViews() {
        textView = findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void findAndSetImage() {
        String imageFilePath = getFilesDir().getPath() + "/image.jpg";
        File file = new File(imageFilePath);

        if (file.exists()) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private void setupReceiver() {
        imageReceiver = new ImageReceiver();
        registerReceiver(imageReceiver, new IntentFilter(getBroadcastName()));
    }

    private class ImageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            findAndSetImage();
        }
    }

    public static String getRandomImageUrl() {
        Random random = new Random();
        int i = random.nextInt(1000);
        return IMAGE_URLS.get(i % 3);
    }

    public static String getBroadcastName() {
        return "IMAGE_LOADER_LOADING_FINISHED";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageReceiver);
    }
}
