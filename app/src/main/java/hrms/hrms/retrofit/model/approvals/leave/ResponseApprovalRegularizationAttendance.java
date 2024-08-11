package hrms.hrms.retrofit.model.approvals.leave;

import java.util.List;

/**
 * Created by yudiz on 05/03/18.
 */

public class ResponseApprovalRegularizationAttendance {

    public int PageNo;
    public String TotalRecord;
    public boolean IsNextPage;
    public List<Details> details;

    public class Details {

        public String RegularisationRequestId;
        public String CategotyId;
        public String Category;
        public String EmployeeName;
        public String EmployeeId;
        public String FromDate;
        public String ToDate;
        public String Description;
        public String Status;
        public String ApprovedDate;
        public String ApprovedBy;
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
