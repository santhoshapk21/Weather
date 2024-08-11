package hrms.hrms.util_lib.imagechooser.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;


import androidx.core.content.FileProvider;

import hrms.hrms.util_lib.imagechooser.listeners.ImageChooserListener;
import hrms.hrms.util_lib.imagechooser.threads.ImageProcessor;

import java.io.File;
import java.util.Calendar;

public class ImageChooserManager implements ImageChooserListener {
    private static final String TAG = "ImageChooserManager";
    private ImageChooserListener listener;

    protected Activity activity;
//    protected Fragment fragment;
//    protected android.app.Fragment appFragment;
    protected int type;
    protected String foldername = "bichooser";
    protected boolean shouldCreateThumbnails;
    protected String filePathOriginal;
    protected Bundle extras;

    public ImageChooserManager(Activity activity, int type, boolean shouldCreateThumbnails) {
        this.activity = activity;
        this.type = type;
        this.shouldCreateThumbnails = shouldCreateThumbnails;
    }


    public void setImageChooserListener(ImageChooserListener listener) {
        this.listener = listener;
    }

    public String choose() throws Exception {
        String path = null;
        if (this.listener == null) {
            throw new IllegalArgumentException("ImageChooserListener cannot be null. Forgot to set ImageChooserListener???");
        } else {
            switch (this.type) {
                case 291:
                    this.choosePicture();
                    break;
                case 292:
                case 293:
                default:
                    throw new IllegalArgumentException("Cannot choose a video in ImageChooserManager");
                case 294:
                    path = this.takePicture();
            }

            return path;
        }
    }

    protected void checkDirectory() {
        File directory = null;
        directory = new File(FileUtils.getDirectory(this.foldername));
        if (!directory.exists()) {
            directory.mkdirs();
        }

    }

    @SuppressLint({"NewApi"})
    protected void startActivity(Intent intent) {
        if (this.activity != null) {
            this.activity.startActivityForResult(intent, this.type);
//        } else if (this.fragment != null) {
//            this.fragment.startActivityForResult(intent, this.type);
//        } else if (this.appFragment != null) {
//            this.appFragment.startActivityForResult(intent, this.type);
        }
    }

    public void reinitialize(String path) {
        this.filePathOriginal = path;
    }

    protected void sanitizeURI(String uri) {
        this.filePathOriginal = uri;
        if (uri.matches("https?://\\w+\\.googleusercontent\\.com/.+")) {
            this.filePathOriginal = uri;
        }

        if (uri.startsWith("file://")) {
            this.filePathOriginal = uri.substring(7);
        }

    }

    @SuppressLint({"NewApi"})
    protected Context getContext() {
        return this.activity != null ? this.activity.getApplicationContext() : null;
    }

    private void choosePicture() throws Exception {
        this.checkDirectory();

        try {
            Intent e = new Intent("android.intent.action.GET_CONTENT");
            e.setType("image/*");
            if (this.extras != null) {
                e.putExtras(this.extras);
            }
            this.startActivity(e);
        } catch (ActivityNotFoundException var2) {
            throw new Exception("Activity not found");
        }
    }

    private String takePicture() throws Exception {
        this.checkDirectory();

        try {
            Intent e = new Intent("android.media.action.IMAGE_CAPTURE");
            this.filePathOriginal = FileUtils.getDirectory(this.foldername) + File.separator + Calendar.getInstance().getTimeInMillis() + ".jpg";
            Uri photoURI = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", new File(this.filePathOriginal));
            e.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            if (this.extras != null) {
                e.putExtras(this.extras);
            }
            this.startActivity(e);
        } catch (ActivityNotFoundException var2) {
            throw new Exception("Activity not found");
        }

        return this.filePathOriginal;
    }

    public void submit(int requestCode, Intent data) {
        if (requestCode != this.type) {
            this.onError("onActivityResult requestCode is different from the type the chooser was initialized with.");
        } else {
            switch (requestCode) {
                case 291:
                    this.processImageFromGallery(data);
                case 292:
                case 293:
                default:
                    break;
                case 294:
                    this.processCameraImage();
            }
        }

    }

    @SuppressLint({"NewApi"})
    private void processImageFromGallery(Intent data) {
        if (data != null && data.getDataString() != null) {
            String uri = data.getData().toString();
            this.sanitizeURI(uri);
            if (this.filePathOriginal != null && !TextUtils.isEmpty(this.filePathOriginal)) {
                Log.i("ImageChooserManager", "File: " + this.filePathOriginal);
                String path = this.filePathOriginal;
                ImageProcessor thread = new ImageProcessor(path, this.foldername, this.shouldCreateThumbnails);
                thread.setListener(this);
                thread.setContext(this.getContext());
                thread.start();
            } else {
                this.onError("File path was null");
            }
        } else {
            this.onError("Image Uri was null!");
        }

    }

    private void processCameraImage() {
        String path = this.filePathOriginal;
        ImageProcessor thread = new ImageProcessor(path, this.foldername, this.shouldCreateThumbnails);
        thread.setListener(this);
        thread.start();
    }

    @Override
    public void onImageChosen(ChosenImage image) {
        if (this.listener != null) {
            this.listener.onImageChosen(image);
        }

    }

    public void onError(String reason) {
        if (this.listener != null) {
            this.listener.onError(reason);
        }

    }
}
