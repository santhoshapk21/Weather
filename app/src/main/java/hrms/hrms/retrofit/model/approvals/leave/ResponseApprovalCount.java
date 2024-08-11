package hrms.hrms.retrofit.model.approvals.leave;

import java.util.List;

/**
 * Created by yudiz on 12/03/18.
 */

public class ResponseApprovalCount {

    public String Message;
    public Long Total;
    public List<Details> details;

    public class Details {
        public String Attendance;
        public String PendingLeave;
        public String CancelledLeave;
        public String Shift;
        public String Correction;
        public String Regularisation;
        public String ExpenseClaim;
    }
}
