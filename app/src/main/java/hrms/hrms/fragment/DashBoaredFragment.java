package hrms.hrms.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.activity.ProfileActivity;
import hrms.hrms.adapter.dashboared.MenuListAdapter;
import hrms.hrms.adapter.dashboared.MenuListAdapterNotManager;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.fragment.attendance.AttendanceFragment;
import hrms.hrms.fragment.claims.ClaimsFragment;
import hrms.hrms.fragment.empdir.EmployeeDirectoryFragment;
import hrms.hrms.fragment.leave.LeaveFragment;
import hrms.hrms.fragment.markAttandance.MarkAttanceFragment;
import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.fragment.shift_change.ShiftChangeFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalCount;
import hrms.hrms.retrofit.model.correctionRequest.ResponseRequestPunch;
import hrms.hrms.retrofit.model.profile.ResponseProfile;
import hrms.hrms.retrofit.model.request.ResponseRequestCount;
import hrms.hrms.util_lib.UtilLib;
import hrms.hrms.utility.Constants;

/**
 * Created by yudiz on 17/02/17.
 */

public class DashBoaredFragment extends BaseFragment implements View.OnClickListener, OnApiResponseListner {
    private com.hris365.databinding.FragmentHomeScreenBinding mBinding;
    private MenuListAdapter mMenuListAdapter;
    private MenuListAdapterNotManager mMenuListAdapterManager;
    private GridLayoutManager mGridLayout;
    private boolean isPresent = false;
    private double value = 0, value2 = 0;
    private float LATITUDE;
    private float LONGITUDE;
    private String addressEncoded;
    private String count;
    private ResponseProfile responseProfile;
    private String cityname;
    private Double lon, lat, radius;
    private String punchtype;
    private LocationManager lm;
    private boolean gpstype;
    private boolean geotype;
    private String approvalcount;
    private List<ResponseApprovalCount.Details> approvalcountList = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
       mBinding.frgHomeIvProfileImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getContext().startActivity(new Intent(getContext(), ProfileActivity.class));
           }
       });
        mBinding.frgHomeIvAttandance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    UtilLib.getPermission(getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}).enqueue(permissionResponse -> {
                        if (permissionResponse.isAllGranted()) {
                            // if (((HomeActivity) getActivity()).getBoolean(BaseAppCompactActivity.TYPE.ISGPSENABLE)) {
                            MarkAttanceFragment markAttanceFragment = new MarkAttanceFragment();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("geotype", geotype);
                            bundle.putBoolean("gpstype", gpstype);
                            bundle.putString("punchtype", punchtype);
                            bundle.putString("cityname", cityname);
                            if (lon !=null && lat!=null) {
                                bundle.putDouble("lon", lon);
                                //bundle.putDouble("lon", 72.585022);
                                bundle.putDouble("lat", lat);
                                //bundle.putDouble("lat", 23.033863);
                                bundle.putDouble("radius", radius);
                            }
                            //bundle.putDouble("radius", 5000);
                            bundle.putBoolean("isPresent", isPresent);
                            markAttanceFragment.setArguments(bundle);
                            markAttanceFragment.setTargetFragment(DashBoaredFragment.this, 1);
                            addFragment(markAttanceFragment, getContext().getString(R.string.str_mark_attendance));
/*
                                    } else {
                                        showPunchingDialog();
                                    }
*/
                        } else {
                            Toast.makeText(getContext(), getContext().getString(R.string.errorRequirePermission), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Please enable your location.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            getContext().startActivity(viewIntent);
                            dismissDialog();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismissDialog();
                        }
                    });
                    builder.show();
                }
                return false;
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        setData();
        setAdapter();
//        getLoc();
        profileApi();
    }



    private void setData() {
        HomeActivity activity = ((HomeActivity) getContext());
        if (activity != null) {
            mBinding.frgHomeTvName.setText(activity.getString(BaseAppCompactActivity.TYPE.USERFULLNAME));
            mBinding.frgHomeTvDept.setText(activity.getString(BaseAppCompactActivity.TYPE.DEPT));
            setImage();
        }
    }

    private void init() {
        mGridLayout = new GridLayoutManager(getContext(), 3);

        mBinding.appbar
                .addOnOffsetChangedListener(
                        new AppBarLayout.OnOffsetChangedListener() {
                            @Override
                            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                                int maxScroll = appBarLayout.getTotalScrollRange();
                                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                                float newPosition = (appBarLayout.getMeasuredWidth() * 0.5f) * percentage;
                                if (value == 0) {
                                    value = mBinding.frgHomeIvProfileImage.getMeasuredWidth() * 1.7;
                                    value2 = mBinding.frgHomeIvProfileImage.getMeasuredHeight();
                                }

                                if (newPosition < value) {
                                    mBinding.frgHomeIvProfileImage.setTranslationX(-newPosition);
                                }

                                if ((1 - percentage) > 0.7) {
                                    mBinding.frgHomeIvProfileImage.setScaleX(1 - percentage);
                                    mBinding.frgHomeIvProfileImage.setScaleY(1 - percentage);
                                }

                                mBinding.frgHomeIvProfileImage.setTranslationY((float) ((value2 * percentage) * 0.85f));

                                if (percentage == 1) {
                                    mBinding.frgHomeIvProfileImage.setTranslationY((float) ((value2 * 1) * 0.85f));
                                    mBinding.frgHomeIvProfileImage.setTranslationX((float) -(value));
                                    mBinding.frgHomeIvProfileImage.setScaleX(0.700f);
                                    mBinding.frgHomeIvProfileImage.setScaleY(0.700f);
                                }
                            }
                        }

                );

    }

    public void setAdapter() {
        if (((HomeActivity) getActivity()) == null)
            return;
        if (((HomeActivity) getActivity()).getBoolean(BaseAppCompactActivity.TYPE.ISMANAGER)) {
            mMenuListAdapter = new MenuListAdapter(getContext(), count, approvalcount, this);
            mBinding.frgHomeRvMenu.setLayoutManager(mGridLayout);
            mBinding.frgHomeRvMenu.setAdapter(mMenuListAdapter);
        } else {
            mMenuListAdapterManager = new MenuListAdapterNotManager(getContext(), count, this);
            mBinding.frgHomeRvMenu.setLayoutManager(mGridLayout);
            mBinding.frgHomeRvMenu.setAdapter(mMenuListAdapterManager);
        }
    }

    @Override
    public void onClick(View v) {

        switch ((int) v.getTag()) {
            case 0:
                addFragment(new AttendanceFragment(), getContext().getString(R.string.attendance));
                break;
            case 1:
                addFragment(new LeaveFragment(), getContext().getString(R.string.leave));
                break;
            case 2:
                addFragment(new ClaimsFragment(), getString(R.string.claims));
                break;
            case 3:
                addFragment(new ShiftChangeFragment(), getString(R.string.shift_change));
                break;
            case 4:
                addFragment(new ApprovalRequestFragment(), getContext().getString(R.string.approvals));
                break;
            case 5:
                addFragment(new EmployeeDirectoryFragment(), getContext().getString(R.string.employee_directory));
                break;
            case 6:
                addFragment(new RequestFragment(), getContext().getString(R.string.request));
                break;
/*
            case 150:
                if (isPresent)
                    attandanceRequest(1);
                else
                    attandanceRequest(0);
                break;
*/
            default:
                if (getContext() != null)
                    //Toast.makeText(getContext(), "Under Development", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void requestCountApi() {
        assert ( getActivity()) != null;
        ((HomeActivity) getActivity()).getApiTask().getRequestCount(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                this
        );
    }

    public void approvalCountApi() {
        ((HomeActivity) getActivity()).getApiTask().getApprovalCount(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                this
        );
    }

    private void profileApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetProfile(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
            if (clsGson != null) {
            if (requestCode == RequestCode.ATTENDANCEREQUESTS) {
                ResponseRequestPunch model = (ResponseRequestPunch) clsGson;
                if (model != null) {
                    if (model.getMessage() != null)
                        Toast.makeText(getContext(), model.getMessage() + "", Toast.LENGTH_SHORT).show();
//                    initDialog();
                }
            }
            if (requestCode == RequestCode.REQUESTCOUNT) {
                ResponseRequestCount model = (ResponseRequestCount) clsGson;

                count = String.valueOf(model.Total);
                Log.e("TAG", count);
                if (mMenuListAdapterManager != null) {
                    mMenuListAdapterManager.addRequestCount(count);
                } else if (mMenuListAdapter != null) {
                    mMenuListAdapter.addRequestCount(count);
                } else {
                    setAdapter();
                }
                dismissDialog();
            }

            if (requestCode == RequestCode.APPROVALCOUNT) {
                ResponseApprovalCount model = (ResponseApprovalCount) clsGson;

                approvalcount = String.valueOf(model.Total);

                Log.e("TAG", approvalcount);
                if (mMenuListAdapter != null) {
                    mMenuListAdapter.addApprovalCount(approvalcount);
                } else {
                    setAdapter();
                }
                requestCountApi();
            }
            if (requestCode == RequestCode.PROFILE) {
                if (responseCode == 200) {
                    ResponseProfile profile = (ResponseProfile) clsGson;
                    //profile.setIsGpsEnable(true);
                    //profile.setGeoFancingEnable(true);
                    setData(profile);
                    Constants.setTimeZones(profile.getServerTime(), profile.getLocationTime());
                    responseProfile = profile;
                    approvalCountApi();
                }
            }
        }
    }

    private void setData(ResponseProfile profile) {
        //gpstype = true;
        //mBinding.frgHomeIvAttandance.setVisibility(View.VISIBLE);

        if (profile!=null && profile.isGeoFancingEnable()) {
            geotype = true;
            if (profile.getmType()!=null && profile.getmType().toLowerCase().equals("bycity")) {
                punchtype = profile.getmType().toLowerCase();
                cityname = profile.getmValue1().toLowerCase();
                mBinding.frgHomeIvAttandance.setVisibility(View.VISIBLE);
            }
            if (profile!= null && profile.getmType()!=null && profile.getmType().toLowerCase().equals("byradius")) {
                punchtype = profile.getmType().toLowerCase();
                lon = Double.parseDouble(profile.getmValue1());
                lat =  Double.parseDouble(profile.getmValue2());
                radius =  Double.parseDouble(profile.getmValue3());
                mBinding.frgHomeIvAttandance.setVisibility(View.VISIBLE);
            }
        } else if (profile!=null && profile.isIsGpsEnable()) {
            gpstype = true;
            mBinding.frgHomeIvAttandance.setVisibility(View.VISIBLE);
        } else {
            mBinding.frgHomeIvAttandance.setVisibility(View.GONE);
//        mBinding.actProfileTvName.setText(profile.getFirstName() + " " + profile.getLastName());
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null) {
            if (((HomeActivity) getActivity()) != null) {

                ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(),
                        errorMessage, Snackbar.LENGTH_SHORT);
            }
        }
    }

    private void attandanceRequest() {
        ((HomeActivity) getActivity()).getApiTask().doGetAttendanceRequests(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                addressEncoded,
                String.valueOf(LATITUDE),
                String.valueOf(LONGITUDE),
                "",
                this
        );
    }

//    public void getLoc() {
//        UtilLib.getLocationManager(getActivity()).getLocation(new OnLocationPickListener() {
//            @Override
//            public void getLastLocation(Location location) {
//                LATITUDE = (float) location.getLatitude();
//                LONGITUDE = (float) location.getLongitude();
//                getAddress(getActivity(), LATITUDE, LONGITUDE);
//            }
//
//            @Override
//            public void onLocationChanged(Location location) {
//                LATITUDE = (float) location.getLatitude();
//                LONGITUDE = (float) location.getLongitude();
//                getAddress(getActivity(), LATITUDE, LONGITUDE);
//            }
//
//            @Override
//            public void onError(String error) {
//            }
//        });
//    }

    private String getAddress(Context context, float lat, float lon) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            addressEncoded = URLEncoder.encode(addresses.get(0).getAddressLine(0), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressEncoded;
    }

    public void isPresent(boolean isPresent) {
        this.isPresent = isPresent;
//        initDialog();
    }

    public void setVisibilityPunchButton(int value) {
        mBinding.frgHomeIvAttandance.setVisibility(value);
    }

    public void setImage() {
        HomeActivity activity = ((HomeActivity) getActivity());
        if (!activity.getString(BaseAppCompactActivity.TYPE.PROFILEURL).equals(""))
            activity.setImage(getContext(), activity.getString(BaseAppCompactActivity.TYPE.PROFILEURL),
                    mBinding.frgHomeIvProfileImage);
        else
            mBinding.frgHomeIvProfileImage.setImageResource(R.drawable.ic_no_image);
    }

    @Override
    public void onResume() {
        super.onResume();
        lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    }
}
