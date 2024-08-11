package hrms.hrms.util_lib.location_helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.Settings;

import hrms.hrms.util_lib.DialogView;
import com.google.android.gms.location.LocationRequest;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.hris365.R;
import hrms.hrms.util_lib.UtilLib;
import hrms.hrms.util_lib.permission_helper.Const;
import hrms.hrms.util_lib.permission_helper.PermissionEverywhere;
import hrms.hrms.util_lib.permission_helper.PermissionResponse;
import hrms.hrms.util_lib.permission_helper.PermissionResultCallback;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class LocationActivity extends Activity {

    private static final long LOCATION_TIME_INTERVAL = 1000 * 60 * 60;         //1 hour
    private LocationManager locationManager;
    private int mNewLocationReceivedCount;
    private static final int MINIMUM_ACCURACY_IN_METERS = 300;
    private FusedLocationProviderClient mFusedLocationClient;

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            onNewLocation(locationResult.getLastLocation());
        }
    };
    private LocationRequest mLocationRequest;
    private ResultReceiver resultReceiver;

    private void getLocation() {
        if (!UtilLib.hasPermission(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION})) {
            PermissionEverywhere.getPermission(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 3).enqueue(new PermissionResultCallback() {
                public void onComplete(PermissionResponse permissionResponse) {
                    if (permissionResponse.isAllGranted()) {
                        LocationActivity.this.getFusedLocation();
                    } else {
                        onError("Location Permission Denied.");
                    }
                }
            });
        } else if (!isLocationEnabled()) {
            showLocationDialog("Please Enable Location Provider.");
//            onError("Please Enable Location Provider.");
        } else {
            this.getFusedLocation();
        }
    }

    static final long LOCATION_UPDATE_INTERVAL = 3000;
    static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = LOCATION_UPDATE_INTERVAL / 2;

    private void getFusedLocation() {
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                mLocationRequest = LocationRequest.create();
                mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
                mLocationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                fetchLocation();
            } catch (SecurityException unlikely) {
                onError("Lost location permission. Could not request updates. " + unlikely);
            }
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient client = LocationServices.getSettingsClient(LocationActivity.this);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
            task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                @SuppressLint("MissingPermission")
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    fetchLocation();
//                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(LocationActivity.this, 125);
                            } catch (IntentSender.SendIntentException sendEx) {
                                onError("Error" + e.getMessage());
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            onError("Error" + e.getMessage());
                            // Location settings are not satisfied. However, we have no way to fix the settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    private void fetchLocation() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(LocationActivity.this, new OnSuccessListener<Location>() {
            @SuppressLint("NewApi")
            @Override
            public void onSuccess(Location location) {
                if (location == null || (Math.abs(System.currentTimeMillis() - location.getTime())) > LOCATION_TIME_INTERVAL)
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                else
                    onNewLocation(location);
            }
        });
    }

    private void onNewLocation(Location location) {
        //This integer tracks how many times onNewLocation has been executed, ensuring FusedLocationProvider doesn't give us a stale Location.
        mNewLocationReceivedCount++;

        if (location.hasAccuracy() && location.getAccuracy() <= MINIMUM_ACCURACY_IN_METERS && mNewLocationReceivedCount > 1) {
            //Since we've received an acceptable location, set the count back to 0 to get ready for next time.
            mNewLocationReceivedCount = 0;

            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            onComplete(0, location.getLatitude(), location.getLongitude(), "", Activity.RESULT_OK);
        } else
            getFusedLocation();


    }

    private void showLocationDialog(String msg) {


        final android.app.Dialog myDialog = new android.app.Dialog(LocationActivity.this, R.style.AppTheme);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = myDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        DialogView permissionDialog = new DialogView(LocationActivity.this);
        permissionDialog.setTitle("Location problem");
        permissionDialog.setMessage(msg);
        permissionDialog.setOkClickListener("Enable", false, new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();

                LocationActivity.this.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
            }
        });
        permissionDialog.setCancleClickListener(LocationActivity.this.getString(android.R.string.cancel), false, new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
                gpsDenied();
            }
        });

        myDialog.setContentView(permissionDialog);
        myDialog.setCancelable(false);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        myDialog.show();
    }

    private boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(LocationActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(LocationActivity.this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.lay_location_progress);
        resultReceiver = getIntent().getParcelableExtra(Const.RESULT_RECEIVER);
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (getIntent() != null) {
            getLocation();
        } else {
//            onComplete(requestCode);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 125) {
            if (resultCode == Activity.RESULT_OK) {
                getLocation();
            } else {
                onError("location priority");
            }
        } else if (requestCode == 1) {
            if (isLocationEnabled())
                getLocation();
            else
                gpsDenied();
        }
    }

    private void gpsDenied() {
        onError("GPS disabled");
        finish();
    }

    private void onError(String error) {
        onComplete(0, 0, 0, error, Activity.RESULT_CANCELED);
    }

    private void onComplete(int requestCode, double lat, double lng, String error, int result) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.REQUEST_CODE, requestCode);
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        bundle.putString("error", error);
        bundle.putInt("result", result);
        resultReceiver.send(requestCode, bundle);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
