
package hrms.hrms.retrofit.model.request;

import java.util.List;

public class ResponseRequestCount {

    public String Message;
    public Long Total;
    public List<Details> details;

    public class Details {
        public String Attendance;
        public String Leave;
        public String Shift;
        public String Correction;
        public String Regularisation;
        public String ExpenseClaim;

    }
}
