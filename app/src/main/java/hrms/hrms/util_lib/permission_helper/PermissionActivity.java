package hrms.hrms.util_lib.permission_helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.view.View;
import android.view.Window;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hris365.R;
import hrms.hrms.util_lib.DialogView;

public class PermissionActivity extends Activity {

    int requestCode;
    String[] permissions;
    int[] grantResults;
    private ResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        if (getIntent() != null) {
            startPermission();
        } else {
            onComplete(requestCode, permissions, grantResults);
//            finish();
        }
    }

    private void startPermission() {
        resultReceiver = getIntent().getParcelableExtra(Const.RESULT_RECEIVER);
        String[] permissionsArray = getIntent().getStringArrayExtra(Const.PERMISSIONS_ARRAY);
        int requestCode = getIntent().getIntExtra(Const.REQUEST_CODE, Const.DEFAULT_CODE);
        if (!hasPermissions(permissionsArray)) {
            ActivityCompat.requestPermissions(this, permissionsArray, requestCode);
        } else {
            onComplete(requestCode, permissionsArray, new int[]{PackageManager.PERMISSION_GRANTED});
        }
    }

    private void onComplete(int requestCode, String[] permissions, int[] grantResults) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(Const.PERMISSIONS_ARRAY, permissions);
        bundle.putIntArray(Const.GRANT_RESULT, grantResults);
        bundle.putInt(Const.REQUEST_CODE, requestCode);
        resultReceiver.send(requestCode, bundle);
        finish();

    }

    private boolean hasPermissions(String[] permissionsArray) {
        boolean result = true;
        for (String permission : permissionsArray) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                result = false;
                break;
            }
        }
        return result;
    }

    private void setComplete(int requestCode, String[] permissions, int[] grantResults) {
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults.length > 0)
                onComplete(requestCode, permissions, grantResults);
        }
    }

    public void showPermissionDialog(String msg) {
        final android.app.Dialog myDialog = new android.app.Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogView permissionDialog = new DialogView(this);
        permissionDialog.setTitle("Permission denied");
        permissionDialog.setMessage(msg);
        permissionDialog.setOkClickListener("RE-TRY", false, new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
                startPermission();
            }
        });
        permissionDialog.setCancleClickListener("I'M SURE", false, new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
                onComplete(requestCode, permissions, grantResults);
            }
        });

        myDialog.setContentView(permissionDialog);
        myDialog.setCancelable(false);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        myDialog.show();
    }

    public void showSettingDialog(String msg) {
        final android.app.Dialog myDialog = new android.app.Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogView permissionDialog = new DialogView(this);
        permissionDialog.setTitle("Permission denied");
        permissionDialog.setMessage(msg);
        permissionDialog.setOkClickListener("Open Settings", false, new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
                startInstalledAppDetailsActivity(PermissionActivity.this);
                onComplete(requestCode, permissions, grantResults);
            }
        });
        permissionDialog.setCancleClickListener(getString(android.R.string.cancel), false, new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
                onComplete(requestCode, permissions, grantResults);
            }
        });

        myDialog.setContentView(permissionDialog);
        myDialog.setCancelable(false);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        myDialog.show();
    }

    public void startInstalledAppDetailsActivity(final Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
}
