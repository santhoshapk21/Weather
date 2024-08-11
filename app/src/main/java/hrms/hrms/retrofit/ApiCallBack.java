package hrms.hrms.retrofit;

import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.activity.LoginActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yudiz on 29/11/16.
 */
public class ApiCallBack implements Callback {

    private OnApiResponseListner onApiResponseListner;
    private int requestCode = 0;

    public ApiCallBack(OnApiResponseListner onApiResponseListner, int requestCode) {
        this.onApiResponseListner = onApiResponseListner;
        this.requestCode = requestCode;
    }

    @Override
    public void onResponse(Call call, Response response) {
            if (isSuccessfull(response)) {
            if (onApiResponseListner != null)
                onApiResponseListner.onResponseComplete(response.body(), requestCode, response.code());
            else
                onApiResponseListner.onResponseError("Error", requestCode);
        }
    }

    private boolean isSuccessfull(Response response) {
        String errorModel = null;
        if (response.isSuccessful())
            return true;
        else {
            try {

                if (response.code() == 401) {
                    if (HomeActivity.context != null) {
                        try {
                            HomeActivity.context.startActivity(new Intent(HomeActivity.context, LoginActivity.class));
                            ((HomeActivity) HomeActivity.context).getPrefrence().edit().clear().commit();
                            Toast.makeText(HomeActivity.context, response.raw().message(), Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ((HomeActivity) HomeActivity.context).finishAffinity();
                            } else {
                                ((HomeActivity) HomeActivity.context).finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                    if (response.raw().message() != null)
                        errorModel = response.raw().message();

                    if (errorModel != null)
                        onApiResponseListner.onResponseError(errorModel + "", requestCode);
                }
            } catch (Exception e) {
                onApiResponseListner.onResponseError("Error in casting.", requestCode);
            }
            return false;
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

        if (!BaseAppCompactActivity.isInternet)
            onApiResponseListner.onResponseError("No internet connection found.", requestCode);
        else if (onApiResponseListner != null && t.getMessage().equals("HTTP 204 had non-zero Content-Length: 29"))
            onApiResponseListner.onResponseError("Data not found.", requestCode);
        else if (onApiResponseListner != null)
            onApiResponseListner.onResponseError(t.getMessage() + "", requestCode);
    }

}

