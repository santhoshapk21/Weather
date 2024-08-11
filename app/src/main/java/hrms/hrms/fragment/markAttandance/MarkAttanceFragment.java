package hrms.hrms.fragment.markAttandance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentMarkAttendanceScreenBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import hrms.com.isseiaoki.simplecropview.CropImageView;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.dialog.CropImageDialog;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.correctionRequest.ResponseRequestPunch;
import hrms.hrms.util_lib.UtilLib;
import hrms.hrms.util_lib.imagechooser.ChooseType;
import hrms.hrms.util_lib.imagechooser.OnImageChooserListener;
import hrms.hrms.util_lib.location_helper.OnLocationPickListener;

/**
 * Created by yudiz on 23/02/17.
 */

public class MarkAttanceFragment extends BaseFragment implements OnApiResponseListner, OnMapReadyCallback {

    private FragmentMarkAttendanceScreenBinding mBinding;
    //    private MapView mapView;
    private GoogleMap googleMap;
    private Location mlocation;
    private Calendar calendar = Calendar.getInstance();
    private boolean isPresent;
    private DashBoaredFragment dashBoaredFragment;
    private Bundle savedInstanceState;
    private LocationManager lm;
    private boolean isFirstTime = true;
    private long interval = 1;
    private File compressedImageFile;
    private String imagePath = "";
    private float lat, lon;
    private String city;
    private String responsecity;
    private float responselon;
    private float responselat;
    private float responseradius;
    private float distance;
    private String punchtype;
    private SupportMapFragment mMapFragment;
    private Location mlocations;
    private String profileLink;
    private boolean gpstype;
    private boolean geotype;
    private Marker marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.frg_mark_attendance_fl_map);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.frg_mark_attendance_fl_map, mMapFragment).commit();
        }
        mMapFragment.getMapAsync(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mark_attendance_screen, null, false);
        ButterKnife.bind(this, mBinding.getRoot());
        this.savedInstanceState = savedInstanceState;
        lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        clickListeners();
        initmap();
        getCurrentLocation();
        mBinding.ivGpsCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });
        mBinding.fragmentMarkAttendanceTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (geotype) {
                    if (punchtype!=null && punchtype.toLowerCase().equals("bycity")) {
                        if (city.toLowerCase().equals(responsecity)) {
                            mBinding.fragmentMarkAttendanceTvSubmit.setClickable(false);
                            if (mBinding.fragmentMarkAttendanceTvDesc.getText().length() > 0) {
                                if (mlocation == null) {
                                    ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(),
                                            getContext().getResources().getString(R.string.str_error_loaction),
                                            Snackbar.LENGTH_SHORT);
                                    mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                                } else {
                                    attandanceRequest();
                                }
                            } else {
                                ((HomeActivity) getActivity()).showSnackBar(
                                        mBinding.getRoot(),
                                        getContext().getResources().getString(R.string.str_error_desc),
                                        Snackbar.LENGTH_SHORT);
                                mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Company location and your location does not match.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dismissDialog();
                                }
                            });
                            builder.show();
                        }
                    }
                    if (punchtype!=null && punchtype.toLowerCase().equals("byradius")) {
                        getdistanceCalculator();
                        if (distance < responseradius) {
                            mBinding.fragmentMarkAttendanceTvSubmit.setClickable(false);
                            if (mBinding.fragmentMarkAttendanceTvDesc.getText().length() > 0) {
                                if (mlocation == null) {
                                    ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(),
                                            getContext().getResources().getString(R.string.str_error_loaction),
                                            Snackbar.LENGTH_SHORT);
                                    mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                                } else {
                                    attandanceRequest();
                                }
                            } else {
                                ((HomeActivity) getActivity()).showSnackBar(
                                        mBinding.getRoot(),
                                        getContext().getResources().getString(R.string.str_error_desc),
                                        Snackbar.LENGTH_SHORT);
                                mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("You are not within the company's radius.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dismissDialog();
                                }
                            });
                            builder.show();
                        }
                    }
                } else if (gpstype) {
                    mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                    attandanceRequest();
                }
                attandanceRequest();
            }
        });
        return mBinding.getRoot();
    }


    private void clickListeners() {

        mBinding.frgMarkAttendanceLlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilLib.getPhoto(getActivity(), ChooseType.REQUEST_CAPTURE_PICTURE)
                        .enqueue(new OnImageChooserListener() {
                            @Override
                            public void onImageChoose(String path) {
                                if (path.length() > 0) {
                                    mBinding.fragmentMarkAttendanceTvSubmit.setClickable(false);
                                    profileLink = path;
                                    CropImageDialog dialog = new CropImageDialog(
                                            getActivity(),
                                            path,
                                            new CropImageDialog.CropImage() {
                                                @Override
                                                public void onImageCrop(String imagePath1) {
                                                    profileLink = imagePath1;
                                                    Picasso.get().load(new File(profileLink)).into(mBinding.frgMarkAttendanceIvPhoto);
                                                    mBinding.frgMarkAttendanceFlPhoto.setVisibility(View.VISIBLE);
                                                    mBinding.frgMarkAttendanceLlPhoto.setVisibility(View.GONE);
                                                    mBinding.frgMarkAttendanceLlPhoto.setEnabled(false);
//                                                    try {
//                                                        compressedImageFile = new Compressor(getActivity()).compressToFile(new File(profileLink));
                                                        imagePath = compressedImageFile.getAbsolutePath();
//                                                    } catch (IOException e) {
//                                                        e.printStackTrace();
//                                                    }
                                                }
                                            }, CropImageView.CropMode.SQUARE);

                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
                                        }
                                    });

                                    dialog.show();
                                }
                            }
                        });
            }
        });

        mBinding.frgMarkAttendanceFlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceImageFragment markAttanceImageFragment = new AttendanceImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("uri", profileLink);
                markAttanceImageFragment.setArguments(bundle);
                addFragment(markAttanceImageFragment, getString(R.string.str_mark_attendance_image));
            }
        });

        mBinding.frgMarkAttendanceIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.frgMarkAttendanceLlPhoto.setEnabled(true);
                mBinding.frgMarkAttendanceFlPhoto.setVisibility(View.GONE);
                mBinding.frgMarkAttendanceLlPhoto.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getCurrentLocation() {
        showDialog();
        UtilLib.getLocationManager(getContext()).enqueue(new OnLocationPickListener() {

            @Override
            public void onLocationChanged(Location location) {
                dismissDialog();
                if (location != null) {
                    mlocation = location;
                    lat = (float) location.getLatitude();
                    lon = (float) location.getLongitude();
                    getLocation();
                    isFirstTime = false;
//                    initmap();
                    if (punchtype!=null && punchtype.toLowerCase().equals("byradius")) {
                        getdistanceCalculator();
                    }
                    if (googleMap != null) {
                        setUpMap(googleMap);
                    }
                }
            }

            @Override
            public void onError(String error) {
                dismissDialog();
                Log.d("Tag", "Location Error." + error);
            }
        });
    }

    private void initmap() {
//        mapView = mBinding.map;
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                googleMap = mMap;
//                setUpMap(mMap);
//            }
//        });
    }

    public void getdistanceCalculator() {
        Location locationA = new Location("point A");
        locationA.setLatitude(responselat);
        locationA.setLongitude(responselon);
        Location locationB = new Location("point B");
        locationB.setLatitude(lat);
        locationB.setLongitude(lon);
        distance = locationA.distanceTo(locationB);

        Log.v("TAG1", "" + distance);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        geotype = getArguments().getBoolean("geotype");
        gpstype = getArguments().getBoolean("gpstype");
        isPresent = getArguments().getBoolean("isPresent");
        responsecity = getArguments().getString("cityname");
        punchtype = getArguments().getString("punchtype");
        responselon = getArguments().getFloat("lon");
        responselat = getArguments().getFloat("lat");
        responseradius = getArguments().getFloat("radius");
        dashBoaredFragment = (DashBoaredFragment) getTargetFragment();

/*
        if (isPresent)
            mBinding.fragmentMarkAttendanceTvSubmit.setText(getString(R.string.str_punch_out));
        else
*/
        mBinding.fragmentMarkAttendanceTvSubmit.setText(getString(R.string.submit));

        init();
    }

    private void init() {
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df1.format(calendar.getTime());
        mBinding.fragmentMarkAttendanceTvDate.setText(formattedDate);
        Date date = new Date();
        date.setHours(date.getHours());
        SimpleDateFormat simpleDateFormatArrivals = new SimpleDateFormat("HH:mm", Locale.UK);
        mBinding.fragmentMarkAttendanceTvTime.setText(simpleDateFormatArrivals.format(date));
    }

    private void setUpMap(GoogleMap mMap) {
        if (marker != null) {
            marker.remove();
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_loaction_map);
        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon))
                .icon(icon)
                .title("")
                .snippet("");
        marker = googleMap.addMarker(markerOptions);

    }

    @Override
    public void onResume() {
        super.onResume();
//        getCurrentLocation();

//        try {
//            if (googleMap == null) {
//                int chkGooglePlayServices = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
//                if (chkGooglePlayServices != ConnectionResult.SUCCESS) {
//                    GooglePlayServicesUtil.getErrorDialog(chkGooglePlayServices, getActivity(), 1122).show();
//                } else {
////                    mMap = mMapFragment.getMap();
//                    mMapFragment.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(GoogleMap mMap) {
//                            googleMap = mMap;
//                            setUpMap(mMap);
//                        }
//                    });
//                }
//            } else
//                setUpMap(googleMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            if (mlocation == null) {
//                UtilLib.getPermission(getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}).enqueue(permissionResponse -> {
//
//                });
//            }
//        } else {
//            Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(viewIntent);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleMap != null)
            mMapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleMap != null)
            mMapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (googleMap != null)
            mMapFragment.onLowMemory();
    }

    public void getLocation() {
        Geocoder geocoder;
        List<Address> addresses = null;

        if (getActivity() != null) {
            try {
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, lon, 1);
                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0) + ", ";
                // If any additional address line present than only, check with
                // max available address lines by getMaxAddressLineIndex()
                address = address + addresses.get(0).getLocality() + ", ";
                address = address + addresses.get(0).getAdminArea() + ", ";
                address = address + addresses.get(0).getCountryName() + ", ";
                address = address + addresses.get(0).getPostalCode() + ", ";
                address = address + addresses.get(0).getFeatureName() + ", ";
                mBinding.frgMarkAttendanceTvLocation.setText(address);

                city = addresses.get(0).getLocality();
            }
        }
    }


    private void attandanceRequest() {
        showDialog();

        ((HomeActivity) getActivity()).getApiTask().doGetAttendanceRequests(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mBinding.frgMarkAttendanceTvLocation.getText().toString(),
                mlocation.getLatitude() + "",
                mlocation.getLongitude() + "",
                mBinding.fragmentMarkAttendanceTvDesc.getText().toString(),
                this
        );
    }

    private void attandancePhoto(String attendanceID) {
        showDialog();

/*
        if (requestTypeId == 1)
            dashBoaredFragment.setVisibilityPunchButton(View.GONE);
        else
            dashBoaredFragment.setVisibilityPunchButton(View.VISIBLE);
*/


        ((HomeActivity) getActivity()).getApiTask().doGetAttendancePhotoRequests(((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN), attendanceID, imagePath,
                this
        );

    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (isVisible())
            mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);

        if (requestCode == RequestCode.ATTENDANCEREQUESTS) {
            if (clsGson != null) {
                ResponseRequestPunch model = (ResponseRequestPunch) clsGson;
                if (model != null) {
                    if (imagePath.length() > 0) {
                        attandancePhoto(model.getGpsAttendanceID());
                    } else {
                        Toast.makeText(getActivity(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                        if (isVisible() && getActivity() != null)
                            ((HomeActivity) getActivity()).onBackPressed();
                    }

                }
            }
        }

        if (requestCode == RequestCode.ATTENDANCEIMAGEREQUESTS) {
            if (clsGson != null) {
                ResponseRequestPunch model = (ResponseRequestPunch) clsGson;
                if (model != null) {
                    if (model.getMessage() != null)
                        Toast.makeText(getContext(),
                                model.getMessage() + "",
                                Toast.LENGTH_SHORT).show();

                    if (isVisible() && getActivity() != null)
                        ((HomeActivity) getActivity()).onBackPressed();
                }
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();

        if (isVisible())
            mBinding.fragmentMarkAttendanceTvSubmit.setClickable(true);
        if (errorMessage != null)
            ((HomeActivity) getActivity()).showSnackBar(
                    mBinding.getRoot(),
                    errorMessage + "",
                    Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftKeyboard(mBinding.getRoot().findFocus());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setUpMap(googleMap);
    }

}
