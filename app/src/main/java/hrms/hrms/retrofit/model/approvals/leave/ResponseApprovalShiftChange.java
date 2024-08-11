package hrms.hrms.retrofit.model.approvals.leave;

import java.util.List;

/**
 * Created by yudiz on 28/02/18.
 */

public class ResponseApprovalShiftChange {

    public int PageNo;
    public String TotalRecord;
    public boolean IsNextPage;
    public List<Details> details;

    public class Details {
        public String ShiftRequestId;
        public String EmployeeId;
        public String ShiftId;
        public String ShiftName;
        public String FromDate;
        public String ToDate;
        public String Reason;
        public String FinalStatus;
        public String EmployeeName;
        public String ShiftStartTime;
        public String ShiftEndTime;
        public String ShiftWorkHours;
        public String ShiftNextdayEnd;
        public String ReportingStatus2;
        public String ReportingStatus1;
        private boolean selected;

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
