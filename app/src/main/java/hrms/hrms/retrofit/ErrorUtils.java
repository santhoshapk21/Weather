package hrms.hrms.retrofit;


import hrms.hrms.retrofit.model.error.ErrorModel;

import java.io.IOException;
import java.lang.annotation.Annotation;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ErrorModel parseError(Response<?> response) {
        Converter<ResponseBody, ErrorModel> converter = BaseAppCompactActivity.getRetrofitInstance().responseBodyConverter(APIError.class, new Annotation[0]);

        ErrorModel error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            error = new ErrorModel();
        }

        return error;
    }
}