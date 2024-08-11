package hrms.hrms.util_lib.location_helper;

import android.location.Location;

public interface OnLocationPickListener {
    void onLocationChanged(Location location);

    void onError(String error);
}