package hrms.hrms.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.hris365.R;


/**
 * Created by Yudiz on 23/07/16.
 */
public class ProgressBarDialog {
    public Dialog dialog;

    public ProgressBarDialog(Context context) {
        dialog = new Dialog(context);
        dialog = new Dialog(context, android.R.style.Theme_Translucent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(Color.parseColor("#80000000"));
        }
    }

    public void showDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.show();
        }
    }


    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }


}
