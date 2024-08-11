package hrms.hrms.util_lib.permission_helper;

import android.content.Context;

public class PermissionEverywhere {
    public static PermissionRequest getPermission(Context context, String[] permissions, int requestCode) {
        return new PermissionRequest(context, permissions, requestCode);
    }
}
