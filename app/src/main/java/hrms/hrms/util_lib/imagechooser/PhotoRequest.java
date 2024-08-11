package hrms.hrms.util_lib.imagechooser;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import hrms.hrms.util_lib.permission_helper.Const;

public class PhotoRequest implements ImageUtilImp{
    Context context;
    ChooseType requestCode;

    public PhotoRequest(Context context, ChooseType requestCode) {
        this.context = context;
        this.requestCode = requestCode;
    }

    public static void sendNotification(Context context, ChooseType requestCode, ResultReceiver receiver) {
        Intent intent = new Intent(context, ChoosePhotoActivity.class);
        intent.putExtra(Const.REQUEST_CODE, requestCode);
        intent.putExtra(Const.RESULT_RECEIVER, receiver);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void enqueue(final OnImageChooserListener callback) {
        sendNotification(context, requestCode, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                String grantResult = resultData.getString(Const.GRANT_RESULT);
                callback.onImageChoose(grantResult);
            }
        });
    }
}

