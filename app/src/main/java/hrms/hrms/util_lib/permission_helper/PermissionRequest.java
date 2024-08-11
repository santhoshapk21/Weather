package hrms.hrms.util_lib.permission_helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import hrms.hrms.util_lib.UtilLib;


public class PermissionRequest implements PermissionUtilImp {
    Context context;
    String[] permissions;
    int requestCode;

    PermissionResponse response;

    public PermissionRequest(Context context, String[] permissions, int requestCode) {
        this.context = context;
        this.permissions = permissions;
        this.requestCode = requestCode;
    }

    private static void sendNotification(Context context, String[] permissions, int requestCode, ResultReceiver receiver) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(Const.REQUEST_CODE, requestCode);
        intent.putExtra(Const.PERMISSIONS_ARRAY, permissions);
        intent.putExtra(Const.RESULT_RECEIVER, receiver);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void enqueue(final PermissionResultCallback callback) {
        if (!UtilLib.hasPermission(context, permissions)) {
            sendNotification(context, permissions, requestCode, new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    super.onReceiveResult(resultCode, resultData);
                    int[] grantResult = resultData.getIntArray(Const.GRANT_RESULT);
                    String[] permissions = resultData.getStringArray(Const.PERMISSIONS_ARRAY);
                    response = new PermissionResponse(permissions, grantResult, resultCode);
                    callback.onComplete(new PermissionResponse(permissions, grantResult, resultCode));
                }
            });
        } else {
            callback.onComplete(new PermissionResponse(permissions, new int[]{PackageManager.PERMISSION_GRANTED}, requestCode));
        }
    }
}
