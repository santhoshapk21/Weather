package hrms.hrms.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Yudiz on 10/10/16.
 */
public class HRMS extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }


}
