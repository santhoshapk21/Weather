package hrms.hrms.retrofit.model.error;

public class ErrorModel {
    public String status;
    public String message;
    public Data data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public class Data{}
}