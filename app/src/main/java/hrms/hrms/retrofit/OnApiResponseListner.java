package hrms.hrms.retrofit;

public interface OnApiResponseListner {
    
    /**
     * On response complete.
     *
     * @param clsGson the cls gson
     * @param requestCode the request code
     */
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode);

    /**
     * On response error.
     *
     * @param errorMessage the error message
     * @param requestCode the request code
     */
    public void onResponseError(String errorMessage, int requestCode);
}