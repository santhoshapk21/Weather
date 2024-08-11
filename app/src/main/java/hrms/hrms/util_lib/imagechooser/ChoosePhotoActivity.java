package hrms.hrms.util_lib.imagechooser;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.hris365.R;
import hrms.hrms.util_lib.imagechooser.api.ChooserType;
import hrms.hrms.util_lib.imagechooser.api.ChosenImage;
import hrms.hrms.util_lib.imagechooser.api.ImageChooserManager;
import hrms.hrms.util_lib.imagechooser.listeners.ImageChooserListener;
import hrms.hrms.util_lib.permission_helper.Const;
import hrms.hrms.util_lib.permission_helper.PermissionEverywhere;
import hrms.hrms.util_lib.permission_helper.PermissionResponse;
import hrms.hrms.util_lib.permission_helper.PermissionResultCallback;

import java.io.*;


public class ChoosePhotoActivity extends Activity implements ImageChooserListener {

    Serializable requestCode;
    private ResultReceiver resultReceiver;
    private Dialog myDialog;
    private ImageChooserManager imageChooserManager;
    private String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestCode = getIntent().getSerializableExtra(Const.REQUEST_CODE);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        if (getIntent() != null) {
            startPhoto();
        } else {
//            onComplete(requestCode);
            finish();
        }
    }
    public void setPhotoPickerDialog() {
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(false);
        myDialog.setContentView(R.layout.choose_photo);

        myDialog.show();
        LinearLayout btnPickPhotoCamera = (LinearLayout) myDialog.findViewById(R.id.choose_photo_ll_camera);
        LinearLayout btnPickPhotoGallery = (LinearLayout) myDialog.findViewById(R.id.choose_photo_ll_gallery);
        LinearLayout btnPickPhotoCancel = (LinearLayout) myDialog.findViewById(R.id.choose_photo_ll_cancel);
        btnPickPhotoCamera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                myDialog.dismiss();
                setPhotoCapture();
            }
        });
        btnPickPhotoGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
                setPickPhoto();
            }
        });
        btnPickPhotoCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
                finish();
            }
        });
    }

    private void startPhoto() {
        resultReceiver = getIntent().getParcelableExtra(Const.RESULT_RECEIVER);
        requestCode = getIntent().getSerializableExtra(Const.REQUEST_CODE);
        if (requestCode == ChooseType.REQUEST_ANY) {
            setPhotoPickerDialog();
        }
        else if (requestCode == ChooseType.REQUEST_CAPTURE_PICTURE) {
            setPhotoCapture();
        } else if (requestCode == ChooseType.REQUEST_PICK_PICTURE) {
            setPickPhoto();
        }
    }

    private void onComplete(int requestCode, String path) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.REQUEST_CODE, requestCode);
        bundle.putString(Const.GRANT_RESULT, path);
        resultReceiver.send(requestCode, bundle);
        finish();
    }


    public void setPhotoCapture() {
        PermissionEverywhere.getPermission(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12)
                .enqueue(new PermissionResultCallback() {
                    @Override
                    public void onComplete(PermissionResponse permissionResponse) {
                        if (permissionResponse.isAllGranted()) {
                            initialize(ChooserType.REQUEST_CAPTURE_PICTURE);
                        } else {
                            ChoosePhotoActivity.this.finish();
                        }
                    }
                });
    }

    public void setPickPhoto() {
        PermissionEverywhere.getPermission(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12)
                .enqueue(new PermissionResultCallback() {
                    @Override
                    public void onComplete(PermissionResponse permissionResponse) {
                        if (permissionResponse.isAllGranted()) {
                            initialize(ChooserType.REQUEST_PICK_PICTURE);
                        } else {
                            ChoosePhotoActivity.this.finish();
                        }
                    }
                });
    }

    private void initialize(int requestCode) {
        imageChooserManager = new ImageChooserManager(this, requestCode, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            mediaPath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (image != null) {
                    rotateImage(image.getFilePathOriginal());
                    onComplete(0, image.getFilePathOriginal());
                }
            }
        }, 100);
    }


    public void rotateImage(String photoPath) {
        try {
            Bitmap bitmap = uriToBitmap(Uri.fromFile(new File(photoPath)));
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
            }
            if (bitmap != null)
                saveBitmap(bitmap, photoPath);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap uriToBitmap(Uri selectedFileUri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            return BitmapFactory.decodeFileDescriptor(fileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }


    private String saveBitmap(Bitmap bitmap, String file_path) {
        File file = new File(file_path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    @Override
    public void onError(String s) {
        Log.d("Tag", "errorm " + s);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                reinitialize(requestCode, data);
            } else {
                onComplete(0, "");
            }
        }
    }

    public void reinitialize(int requestCode, Intent data) {
        if (imageChooserManager == null) {
            imageChooserManager = new ImageChooserManager(this, requestCode, true);
            imageChooserManager.setImageChooserListener(this);
            imageChooserManager.reinitialize(mediaPath);
        }
        imageChooserManager.submit(requestCode, data);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
