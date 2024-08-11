package hrms.hrms.retrofit;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yudizsolutions on 11/04/16.
 */
public class WebAPI {

//        public static final String BASE_URL = "http://hris365mobile.hrmsystem.in/v1/";

    //    public static final String BASE_URL = " http://192.168.11.95:8086/";
//    public static final String BASE_URL = "http://api.hris365.com/v5/";
    public static final String BASE_URL = "https://api.skillwill.live/";
    public static final String BASE_URL_2 = "https://api2.skillwill.live/";
    private static final List<String> API_2_LIST = Arrays.asList(
            "api/Action/Approvals",
            "api/Action/GPSAttendance",
            "api/Action/ExpenseClaim",
            "api/Approval/Count",
            "api/Approval/MarkAttendance",
            "api/Approval/ExpenseClaim",
            "api/Approval/ExpenseClaimByID",
            "api/Attendance/UploadImage",
            "api/Career/GetDesignation",
            "api/Career/GetCountry",
            "api/Career/GetState",
            "api/Career/GetCity",
            "api/Career/GetLanguage",
            "api/Career/GetDegreeList",
            "api/Career/InsertResumeMaster",
            "api/Career/UploadAttachment",
            "api/Claims/ExpenseList",
            "api/Claims/TravelMode",
            "api/Claims/CliamList",
            "api/Claims/CliamListByID",
            "api/Leave/LeaveBalance",
            "api/Request/AllRequest",
            "api/Request/Count",
            "api/Request/MonthlyCount",
            "api/Request/ExpenseClaim",
            "api/Request/UploadExpenseImage",
            "api/Request/Detail"
    );
    public static final String LOGIN = BASE_URL + "api/User/Login";

    public static final String LOGOUT = BASE_URL + "api/User/Logout";

    public static final String CHANGEPASSWORD = BASE_URL + "api/User/ChangePassword";

    public static final String RESETPASSWORD = BASE_URL + "api/User/ResetPassword";

    public static final String PROFILE = BASE_URL + "api/Profile/GetProfile";

    public static final String UPDATEPROFILE = BASE_URL + "api/Profile/Update";

    public static final String COMPANYDIRECTORY = BASE_URL + "api/Profile/Directory";

    public static final String ATTENDANCE = BASE_URL + "api/Attendance/List";

    public static final String ATTENDANCEREQUESTS = BASE_URL + "api/Attendance/Requests";

    public static final String ATTENDANCEPHOTOREQUESTS = BASE_URL + "api/Attendance/UploadImage";

    public static final String ATTENDANCEREQUESTED = BASE_URL + "api/Attendance/Requested";

    public static final String CORRECTIONREQUEST = BASE_URL + "api/Attendance/CorrectionRequest";

    public static final String LEAVE = BASE_URL + "api/Approval/Leave";

    public static final String SHIFT = BASE_URL + "api/Approval/Shift";

    public static final String REGULARISATION = BASE_URL + "api/Approval/Regularisation";

    public static final String CORRECTION = BASE_URL + "api/Approval/Correction";

    public static final String APPVERSION = BASE_URL + "api/App/Version";

    public static final String CORRECTIONCATEGORY = BASE_URL + "api/Attendance/CorrectionCategory";

    public static final String REGULARISATIONCATEGORY = BASE_URL + "api/Attendance/RegularisationCategory";

    public static final String REGULARISATIONREQUEST = BASE_URL + "api/Attendance/RegularisationRequest";

    public static final String REGISTERTOKEN = BASE_URL + "api/User/DeviceToken";

    public static final String ISPRESENT = BASE_URL + "api/Attendance/IsPresent";

    public static final String REQUESTCOUNT = BASE_URL_2 + "api/Request/Count";

    public static final String REQUESTMONTHLYCOUNT = BASE_URL_2 + "api/Request/MonthlyCount";

    public static final String APPROVALCOUNT = BASE_URL_2 + "api/Approval/Count";

    public static final String GETPROFILEOTHERS = BASE_URL + "api/Profile/GetOthersProfile";

    public static final String CHANGEPROFILEIMAGE = BASE_URL + "api/Profile/UpdateImage";

    public static final String SEARCHDIRECTORY = BASE_URL + "api/Profile/EmployeeSearch";

    public static final String REQUESTLEAVE = BASE_URL + "api/Request/Leave";

    public static final String REQUESTSHIFTCHANGE = BASE_URL + "api/Request/ShiftChange";

    public static final String REQUESTCORRECTION = BASE_URL + "api/Request/Correction";

    public static final String REQUESTREGULARISATION = BASE_URL + "api/Request/Regularisation";

    public static final String REQUESTATTENDANCE = BASE_URL + "api/Request/Attendance";

    public static final String APPROVALLEAVE = BASE_URL + "api/Approval/Leave";

    public static final String APPROVALSHIFT = BASE_URL + "api/Approval/Shift";

    public static final String APPROVALCORRECTION = BASE_URL + "api/Approval/Correction";

    public static final String APPROVALREGULARISATION = BASE_URL + "api/Approval/Regularisation";

    public static final String APPROVALMARKATTENDANCE = BASE_URL_2 + "api/Approval/MarkAttendance";

    public static final String REQUESTALL = BASE_URL_2 + "api/Request/AllRequest";

    public static final String ANNUALLEAVE = BASE_URL + "api/Leave/Annual";

    public static final String LEAVEDETAILS = BASE_URL_2 + "api/Leave/LeaveBalance";

    public static final String LEAVEDETAIL = BASE_URL_2 + "api/Request/Detail";

    public static final String LEAVECANCEL = BASE_URL + "api/Leave/Cancel";

    public static final String ACTIONAPPROVAL = BASE_URL_2 + "api/Action/Approvals";

    public static final String ACTIONAPPROVALMARKATTENDANCE = BASE_URL_2 + "api/Action/GPSAttendance";

    public static final String APPLYLEAVE = BASE_URL + "api/Leave/Apply";

    public static final String SHIFTCHANGELIST = BASE_URL + "api/Shift/Retrieve";

    public static final String SHIFTAVAILABLE = BASE_URL + "api/Shift/Available";

    public static final String SHIFTUPDATE = BASE_URL + "api/Shift/Update";

    public static final String SHIFTCHANGEDETAIL = BASE_URL + "api/Request/ShiftChangeDetail";

    public static final String ATTENDANCECORRECTIONDETAIL = BASE_URL + "api/Request/CorrectionDetail";

    public static final String REGULARISATIONDETAIL = BASE_URL + "api/Request/RegularisationDetail";

    public static final String MARKATTENDACNEDETAIL = BASE_URL + "api/Attendance/Detail";

    public static final String CLAIMLIST = BASE_URL_2 + "api/Claims/CliamList";

    public static final String ADDCLAIMDETAIL = BASE_URL_2 + "api/Request/ExpenseClaim";

    public static final String GETMODEOFTRAVEL = BASE_URL_2 + "api/Claims/TravelMode";

    public static final String GETEXPENSELIST = BASE_URL_2 + "api/Claims/ExpenseList";

    public static final String UPLOADCLAIMATTACHMENTLIST = BASE_URL_2 + "api/Request/UploadExpenseImage";

    public static final String APPROVAL_EXPENSE_CLAIM_LIST = BASE_URL_2 + "api/Approval/ExpenseClaim";

    public static final String ACTION_EXPENSE_CLAIM = BASE_URL_2 + "api/Action/ExpenseClaim";

}
