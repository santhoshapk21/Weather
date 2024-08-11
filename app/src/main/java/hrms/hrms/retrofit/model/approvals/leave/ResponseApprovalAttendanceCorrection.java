package hrms.hrms.retrofit.model.approvals.leave;

import java.util.List;

/**
 * Created by yudiz on 28/02/18.
 */

public class ResponseApprovalAttendanceCorrection {

    public int PageNo;
    public String TotalRecord;
    public boolean IsNextPage;
    public List<Details> details;

    public class Details {
        public String CorrectionReqId;
        public String RequestType;
        public String EmployeeName;
        public String EmployeeId;
        public String RequestDate;
        public String RequestTime;
        public String NextDayOut;
        public String Reason;
        public String Status;
        public String ApprovedBy;

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
