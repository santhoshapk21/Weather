package hrms.hrms.enums;

/**
 * Created by Yudiz on 07/10/16.
 */
public enum EmployeeAttendanceStatus {
    HOLIDAY("holiday"), PENDINGLEAVE("Pending_leave"), APPROVEDLEAVE("approved_leave");
    public final String value;

    EmployeeAttendanceStatus(String value) {
        this.value = value;
    }


}

