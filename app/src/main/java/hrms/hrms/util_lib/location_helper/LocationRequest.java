package hrms.hrms.util_lib.location_helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import hrms.hrms.util_lib.permission_helper.Const;


public class LocationRequest implements LocationUtilImp {
    Context context;

    public LocationRequest(Context context) {
        this.context = context;
    }

    public static void sendNotification(Context context, ResultReceiver receiver) {
        Intent intent = new Intent(context, LocationActivity.class);
        intent.putExtra(Const.RESULT_RECEIVER, receiver);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void enqueue(final OnLocationPickListener callback) {
        sendNotification(context, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);

                double lat = resultData.getDouble("lat");
                double lng = resultData.getDouble("lng");
                Location location = new Location("");
                location.setLatitude(lat);
                location.setLongitude(lng);

                String error = resultData.getString("error");
                int result = resultData.getInt("result");

                if (result == Activity.RESULT_OK)
                    callback.onLocationChanged(location);
                else
                    callback.onError(error);
            }
        });
    }
}

