package hrms.hrms.util_lib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import hrms.hrms.util_lib.imagechooser.ChooseType;
import hrms.hrms.util_lib.imagechooser.ImageUtilImp;
import hrms.hrms.util_lib.imagechooser.PhotoRequest;
import hrms.hrms.util_lib.location_helper.LocationRequest;
import hrms.hrms.util_lib.location_helper.LocationUtilImp;
import hrms.hrms.util_lib.permission_helper.PermissionRequest;
import hrms.hrms.util_lib.permission_helper.PermissionUtilImp;

public class UtilLib {
    public UtilLib() {
    }

    public static ImageUtilImp getPhoto(Context context, ChooseType type) {
        return new PhotoRequest(context, type);
    }

    public static LocationUtilImp getLocationManager(Context context) {
        return new LocationRequest(context);
    }

    public static PermissionUtilImp getPermission(Context context, String[] permissions) {
        return new PermissionRequest(context, permissions, 3);
    }

    public static boolean hasPermission(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                boolean result = (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
                if (!result) {
                    return false;
                }
            }
        }
        return true;
    }
}