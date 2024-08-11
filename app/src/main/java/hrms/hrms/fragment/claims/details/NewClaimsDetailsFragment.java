package hrms.hrms.fragment.claims.details;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentNewClaimDetailsBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import hrms.com.isseiaoki.simplecropview.CropImageView;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.dialog.CropImageDialog;
import hrms.hrms.interfaces.OnBackPressListerner;
import hrms.hrms.model.ClaimDetails;
import hrms.hrms.model.ExpenseList;
import hrms.hrms.model.ExpenseListDetail;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.widget.SpinnerAdapter;

/**
 * Created by Yudiz on 07/10/16.
 */
@SuppressLint("ValidFragment")
public class NewClaimsDetailsFragment extends BaseFragment {

    private FragmentNewClaimDetailsBinding mBinding;
    private SpinnerAdapter arrayAdapter;
    private ClaimDetails claimDetails;
    private OnBackPressListerner onBackPressListerner;
    private String selecetedimagepath;
    private File compressedImageFile;
    private String compressedimagePath = "";
    ExpenseList expenseList;
    String expenseName;
    final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1009;
    final int REQUEST_IMAGE_CAPTURE = 109;
    final int PERMISSION_REQUEST_CODE_GALLERY = 1090;
    final int PERMISSION_REQUEST_GALLERY = 1122;
    CropImageDialog cropImageDialog;

    public NewClaimsDetailsFragment(ClaimDetails claimDetails, OnBackPressListerner onBackPressListerner) {
        this.claimDetails = claimDetails;
        this.onBackPressListerner = onBackPressListerner;
    }

    public NewClaimsDetailsFragment(OnBackPressListerner onBackPressListerner) {
        this.claimDetails = new ClaimDetails();
        this.onBackPressListerner = onBackPressListerner;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @
            Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_claim_details, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
//        setUpSpinner();
        ExpenseListApiCall();
        setUpImageCancelClick();
        mBinding.fragmentNewClaimDetailsTvAddClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    SetCLaimDetailsData();
                    if (onBackPressListerner != null) {
                        onBackPressListerner.onBackPressListerner(claimDetails);
                    }
                    getActivity().onBackPressed();
                }
            }
        });
        mBinding.fragmentNewClaimDetailsLlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    dispatchTakePictureIntent();
                } else {
                    if (checkAndRequestPermissions(getActivity())) {
                        dispatchTakePictureIntent();
                    }
                }
            }
        });
        mBinding.fragmentNewClaimDetailsLlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (checkAndRequestPermissionsForGallery(true)) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PERMISSION_REQUEST_GALLERY);
                    }
                } else {
                    if (checkAndRequestPermissionsForGallery(false)) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PERMISSION_REQUEST_GALLERY);
                    }
                }
            }
        });
        return mBinding.getRoot();
    }

    private boolean checkAndRequestPermissionsForGallery(boolean isAbove13) {
        if (isAbove13) {
            // Check if the permission is already granted
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        PERMISSION_REQUEST_CODE_GALLERY);
                return false;
            } else {
                return true;
                // Permission is granted, proceed with accessing media
            }
        } else {
            // Check if the permission is already granted
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE_GALLERY);
                return false;
            } else {
                return true;
                // Permission is granted, proceed with accessing media
            }
        }
    }

    private boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    mBinding.fragmentNewClaimDetailsLlCamera.performClick();
                }
                break;
            case PERMISSION_REQUEST_CODE_GALLERY:
                if ((ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED)
                ) {
                    mBinding.fragmentNewClaimDetailsLlGallery.performClick();
                }
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        compressedImageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        selecetedimagepath = compressedImageFile.getAbsolutePath();
        return compressedImageFile;
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setImageIfSelected(selecetedimagepath);

        } else if (requestCode == PERMISSION_REQUEST_GALLERY && resultCode == RESULT_OK) {
            selecetedimagepath = getRealPathFromURI(data.getData());
            compressedImageFile = new File(selecetedimagepath);
            setImageIfSelected(selecetedimagepath);

        } else if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        //imageView.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
//                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                setImageIfSelected(picturePath);
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void ExpenseListApiCall() {
        try {
            if (getArguments() != null) {
                showDialog();
                ((HomeActivity) getActivity()).getApiTask().getExpenseDetailList(((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN), getArguments().getString("CITYID"), new OnApiResponseListner() {
                    @Override
                    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
                        dismissDialog();
                        if (requestCode == RequestCode.EXPENSELIST) {
                            ((HomeActivity) getActivity()).dismissDialog();
                            expenseList = (ExpenseList) clsGson;
                            if (expenseList != null && expenseList.getDetails().size() > 0)
                                setAdapter(expenseList.getDetails());
                            else {
                            }
                        }
                    }

                    @Override
                    public void onResponseError(String errorMessage, int requestCode) {
                        ((HomeActivity) getActivity()).dismissDialog();
                    }
                });
            } else {

            }
        } catch (Exception e) {

        }


    }

    private void setAdapter(List<ExpenseListDetail> Expenselistdetails) {

        ArrayList<String> categoryList = new ArrayList<>();

        for (ExpenseListDetail expenseListDetail : Expenselistdetails) {
            categoryList.add(expenseListDetail.getName());
        }

        arrayAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                categoryList
        );
        mBinding.fragmentNewClaimDetailsSpnClaimType
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mBinding.frgmentNewClaimDetailsTvEligibleamount.setText(expenseList.getDetails().get(position).getEligibleAmount());
                        expenseName = expenseList.getDetails().get(position).getName();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        mBinding.fragmentNewClaimDetailsSpnClaimType.setAdapter(arrayAdapter);


    }


    private void setUpImageCancelClick() {
        mBinding.fragmentNewClaimDetailsIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.fragmentNewClaimDetailsFlSelectedpicview.setVisibility(View.GONE);
                mBinding.frgNewClaimDetailsLlImageselection.setVisibility(View.VISIBLE);
                compressedImageFile = null;
            }
        });
    }

//    private void setUpSpinner() {
//
//        String[] claimType = getActivity().getResources().getStringArray(R.array.claim_type);
//        arrayAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, claimType);
//        arrayAdapter.setFont(getString(R.string.gotham_book));
//        mBinding.fragmentNewClaimDetailsSpnClaimType.setAdapter(arrayAdapter);
//        mBinding.fragmentNewClaimDetailsRvAddClaim.setOnRippleCompleteListener(this);
//
//    }

    private boolean checkValidation() {

        if (expenseList == null) {
            showSnackBar(getResources().getString(R.string.selectmodeoftravel));
            return false;
        } else if (TextUtils.isEmpty(mBinding.frgmentNewClaimDetailsEdAmount.getText())) {
            showSnackBar(getResources().getString(R.string.enterexpenseamount));
            return false;
        }
        return true;

    }

    private void SetCLaimDetailsData() {
        if (!TextUtils.isEmpty(mBinding.fragmentNewClaimDetailsSpnClaimType.getSelectedItem().toString().trim())) {
            claimDetails.setType(mBinding.fragmentNewClaimDetailsSpnClaimType.getSelectedItem().toString().trim());
        }
        if (!TextUtils.isEmpty(mBinding.frgmentNewClaimDetailsEdAmount.getText().toString().trim())) {
            claimDetails.setAmount(mBinding.frgmentNewClaimDetailsEdAmount.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(mBinding.frgmentNewClaimDetailsEdDesc.getText().toString().trim())) {
            claimDetails.setDesc(mBinding.frgmentNewClaimDetailsEdDesc.getText().toString().trim());
        }

        if (!TextUtils.isEmpty(compressedimagePath)) {
            claimDetails.setAttachment(compressedimagePath);
        }
        if (!TextUtils.isEmpty(expenseName)) {
            claimDetails.setExpenseName(expenseName);
        }
        if (!TextUtils.isEmpty(mBinding.frgmentNewClaimDetailsEdRecieptNo.getText())) {
            claimDetails.setReceiptNumber(mBinding.frgmentNewClaimDetailsEdRecieptNo.getText().toString());
        }


    }

    public void setImageIfSelected(String path) {
        if (path.length() > 0) {
            selecetedimagepath = path;
            cropImageDialog = new CropImageDialog(
                    getActivity(),
                    path,
                    new CropImageDialog.CropImage() {
                        @Override
                        public void onImageCrop(String imagePath1) {
                            selecetedimagepath = imagePath1;
                            Picasso.get().load(new File(selecetedimagepath)).into(mBinding.fragmentNewClaimDetailsIvPhoto);
                            mBinding.fragmentNewClaimDetailsFlSelectedpicview.setVisibility(View.VISIBLE);
                            mBinding.frgNewClaimDetailsLlImageselection.setVisibility(View.GONE);
                            mBinding.fragmentNewClaimDetailsIvPhoto.setEnabled(false);
//                            try {
//                                compressedImageFile = new Compressor(getActivity()).compressToFile(new File(imagePath1));
                            compressedimagePath = compressedImageFile.getAbsolutePath();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            cropImageDialog.dismiss();
                        }
                    }, CropImageView.CropMode.SQUARE);

            cropImageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
//                            mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                }
            });

            cropImageDialog.show();
        }
    }

    private void showSnackBar(String message) {
        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT);
    }
}
