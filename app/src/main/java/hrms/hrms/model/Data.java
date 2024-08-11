package hrms.hrms.model;

import java.io.Serializable;

public class Data implements Serializable {
        private String LeaveDate;
        private String LeaveDuration;

        public String getLeaveDate() {
            return LeaveDate;
        }

        public void setLeaveDate(String leaveDate) {
            LeaveDate = leaveDate;
        }

        public String getLeaveDuration() {
            return LeaveDuration;
        }

        public void setLeaveDuration(String leaveDuration) {
            LeaveDuration = leaveDuration;
        }
    }