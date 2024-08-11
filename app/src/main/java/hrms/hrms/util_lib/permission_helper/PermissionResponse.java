package hrms.hrms.util_lib.permission_helper;

import android.content.pm.PackageManager;

public class PermissionResponse {
    String[] permission;
    int[] grantResult;
    int requestCode;

    public PermissionResponse(String[] permission, int[] grantResult, int requestCode) {
        this.permission = permission;
        this.grantResult = grantResult;
        this.requestCode = requestCode;
    }

    public boolean isAllGranted() {
        if (grantResult != null && grantResult.length > 0) {
            for (int permission : grantResult) {
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public String[] getPermission() {
        return permission;
    }

    public int[] getGrantResult() {
        return grantResult;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
