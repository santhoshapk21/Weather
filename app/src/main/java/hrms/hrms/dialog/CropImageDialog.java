package hrms.hrms.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hris365.R;

import java.io.File;
import java.io.FileOutputStream;

import hrms.com.isseiaoki.simplecropview.CropImageView;
import hrms.com.isseiaoki.simplecropview.callback.LoadCallback;


public class CropImageDialog extends Dialog {

    private final String imagePath;
    private final CropImage cropImage;
    private CropImageView cropImageView;

    private CropImageView.CropMode cropType;

    public CropImageDialog(Context context, String imagePath, CropImage cropImage, CropImageView.CropMode cropType) {
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.imagePath = imagePath;
        this.cropImage = cropImage;
        this.cropType = cropType;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_crop_image);

        cropImageView = (CropImageView) findViewById(R.id.civ_crop_image);
        ImageButton ibSaveCropImage = (ImageButton) findViewById(R.id.ib_save_image);
        ImageButton ibClose = (ImageButton) findViewById(R.id.ib_close);

        cropImageView.startLoad(Uri.fromFile(new File(imagePath)), new LoadCallback() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess() {

            }

        });
        cropImageView.setCropMode(cropType);

        ibSaveCropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage.onImageCrop(bitmapConvertToFile(cropImageView.getCroppedBitmap()).getPath());
            }
        });
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    private File bitmapConvertToFile(Bitmap bitmap) {
        FileOutputStream fileOutputStream;
        File bitmapFile = new File(imagePath);
        try {
            fileOutputStream = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapFile;
    }


    public interface CropImage {
        void onImageCrop(String imagePath);
    }

}
