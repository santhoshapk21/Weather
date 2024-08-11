package hrms.hrms.fragment.claims.details;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andexert.library.RippleView;
import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentNewClaimDetailsBinding;
import com.rey.material.widget.Spinner;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import hrms.hrms.util_lib.UtilLib;
import hrms.hrms.util_lib.imagechooser.ChooseType;
import hrms.hrms.util_lib.imagechooser.OnImageChooserListener;
import hrms.hrms.widget.SpinnerAdapter;
/**
 * Created by Yudiz on 07/10/16.
 */
@SuppressLint("ValidFragment")
public class NewClaimsDetailsFragment extends BaseFragment implements RippleView.OnRippleCompleteListener {

    private FragmentNewClaimDetailsBinding mBinding;
    private SpinnerAdapter arrayAdapter;
    private ClaimDetails claimDetails;
    private OnBackPressListerner onBackPressListerner;
    private String selecetedimagepath;
    private File compressedImageFile;
    private String compressedimagePath = "";
    ExpenseList expenseList;
    String expenseName;


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
        mBinding.fragmentNewClaimDetailsRvAddClaim.setOnRippleCompleteListener(this);
        mBinding.fragmentNewClaimDetailsLlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilLib.getPhoto(getActivity(), ChooseType.REQUEST_CAPTURE_PICTURE).enqueue(new OnImageChooserListener() {
                    @Override
                    public void onImageChoose(String path) {
                        log(path);
                        setImageIfSelected(path);

                    }


                });
            }
        });
        mBinding.fragmentNewClaimDetailsLlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilLib.getPhoto(getActivity(), ChooseType.REQUEST_PICK_PICTURE).enqueue(new OnImageChooserListener() {
                    @Override
                    public void onImageChoose(String path) {
                        log(path);
                        setImageIfSelected(path);
                    }
                });
            }
        });
        return mBinding.getRoot();
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

    @Override
    public void onComplete(RippleView rippleView) {

        if (checkValidation()) {
            SetCLaimDetailsData();
            if (onBackPressListerner != null) {
                onBackPressListerner.onBackPressListerner(claimDetails);
            }
            getActivity().onBackPressed();
        }


    }

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
            CropImageDialog dialog = new CropImageDialog(
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
                        }
                    }, CropImageView.CropMode.SQUARE);

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
//                            mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                }
            });

            dialog.show();
        }
    }

    private void showSnackBar(String message) {
        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT);
    }
}
