package hrms.hrms.retrofit.task;

import static hrms.hrms.baseclass.BaseAppCompactActivity.getRetrofitInstance;
import static hrms.hrms.baseclass.BaseAppCompactActivity.getRetrofitInstance2;

import java.io.File;
import java.util.List;

import hrms.hrms.model.AddClaimDetails;
import hrms.hrms.model.ClaimExpenseResponse;
import hrms.hrms.model.ExpenseList;
import hrms.hrms.model.LeaveDetails;
import hrms.hrms.model.ModeOfTravel;
import hrms.hrms.retrofit.ApiCallBack;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.call.ApiCall;
import hrms.hrms.retrofit.model.Leave.AnnualLeave.ResponseAnnualLeave;
import hrms.hrms.retrofit.model.Leave.LeaveDetails.ResponseLeaveDetails;
import hrms.hrms.retrofit.model.approvals.leave.ActionExpenseClaim;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionExpenseClaim;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalAttendanceCorrection;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalCount;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalExpenseClaim;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalLeave;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalMarkAttendance;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalRegularizationAttendance;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalShiftChange;
import hrms.hrms.retrofit.model.attendance.ResponseAttendance;
import hrms.hrms.retrofit.model.category.ResponseCategory;
import hrms.hrms.retrofit.model.claim.ResponseClaimList;
import hrms.hrms.retrofit.model.companyDirectory.ResponseCompanyDirectory;
import hrms.hrms.retrofit.model.correctionRequest.ResponseRequestPunch;
import hrms.hrms.retrofit.model.isPresent.ResponseIsPresent;
import hrms.hrms.retrofit.model.login.ResponseLogin;
import hrms.hrms.retrofit.model.profile.ResponseProfile;
import hrms.hrms.retrofit.model.regularisationCategory.ResponseRegularisationCategory;
import hrms.hrms.retrofit.model.request.Correction.ResponseRequestCorrection;
import hrms.hrms.retrofit.model.request.ResponseRequest;
import hrms.hrms.retrofit.model.request.ResponseRequestCount;
import hrms.hrms.retrofit.model.request.attendance.ResponseRequestAttendance;
import hrms.hrms.retrofit.model.request.leave.ResponseLeaveCancel;
import hrms.hrms.retrofit.model.request.leave.ResponseRequestLeave;
import hrms.hrms.retrofit.model.request.regularisation.ResponseRequestRegularisation;
import hrms.hrms.retrofit.model.request.shiftChange.ResponseRequestedShiftChange;
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;
import hrms.hrms.retrofit.model.shiftChange.ResponseShiftAvailable;
import hrms.hrms.retrofit.model.shiftChange.ResponseShiftChange;
import hrms.hrms.retrofit.model.versionUpdate.ResponseVersion;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ApiTask {

    private final ApiCall apiCall;
    private final ApiCall apiCall2;

    public ApiTask() {
        Retrofit retrofit = getRetrofitInstance();
        Retrofit retrofit2 = getRetrofitInstance2();
        apiCall = retrofit.create(ApiCall.class);
        apiCall2 = retrofit2.create(ApiCall.class);
    }

    public Call<?> doLogin(String domain, String username, String password,
                           OnApiResponseListner onApiResponseListner) {
        Call<ResponseLogin> doLogin = apiCall2.doLogin(domain, username, password);
        doLogin.enqueue(new ApiCallBack(onApiResponseListner, RequestCode.LOGIN));
        return doLogin;
    }

    public Call<?> doLogout(String accessToken, OnApiResponseListner listner) {
        Call<ResponseLogin> doLogout = apiCall.doLogout(accessToken);
        doLogout.enqueue(new ApiCallBack(listner, RequestCode.LOGOUT));
        return doLogout;
    }

    public Call<?> doResetPassword(String domain, String username, OnApiResponseListner listner) {
        Call<ResponseResetPassword> doResetPassword = apiCall.doResetPassword(domain, username);
        doResetPassword.enqueue(new ApiCallBack(listner, RequestCode.RESETPASSWORD));
        return doResetPassword;
    }

    public Call<?> doGetProfile(String accessToken, OnApiResponseListner listner) {
        Call<ResponseProfile> doGetProfile = apiCall2.doGetProfile(accessToken);
        doGetProfile.enqueue(new ApiCallBack(listner, RequestCode.PROFILE));
        return doGetProfile;
    }

    public Call<?> doRequestLeaveDetails(String accessToken, String leavecode, OnApiResponseListner listner) {
        Call<?> doRequestLeaveDetails = apiCall2.doRequestLeaveDetails(accessToken, leavecode);
        doRequestLeaveDetails.enqueue(new ApiCallBack(listner, RequestCode.REQUESTLEAVEDEATIL));
        return doRequestLeaveDetails;
    }

    public Call<?> doRequestLeaveDetail(String accessToken, String leavecode, OnApiResponseListner listner) {
        Call<?> doRequestLeaveDetail = apiCall2.doRequestLeaveDetail(accessToken, leavecode);
        doRequestLeaveDetail.enqueue(new ApiCallBack(listner, RequestCode.REQUESTLEAVEDEATIL));
        return doRequestLeaveDetail;
    }

    public Call<?> getRequestLeaveCancel(String accessToken, String Description, String ID, OnApiResponseListner listner) {
        Call<ResponseLeaveCancel> getRequestLeaveCancel = apiCall.getRequestLeaveCancel(accessToken, Description, ID);
        getRequestLeaveCancel.enqueue(new ApiCallBack(listner, RequestCode.REQUESTLEAVECANCEL));
        return getRequestLeaveCancel;
    }

    public Call<?> getActionApproval(String accessToken, String LvDescription, String LeaveAppID, String Status, String Type, OnApiResponseListner listner) {
        Call<ResponseActionApproval> getActionApproval = apiCall2.getActionApproval(accessToken, LvDescription, LeaveAppID, Status, Type);
        getActionApproval.enqueue(new ApiCallBack(listner, RequestCode.ACTIONAPPROVAL));
        return getActionApproval;
    }

    public Call<?> getMarkAttendanceActionApproval(String accessToken, String LvDescription, String LeaveAppID, String Status, String Type, OnApiResponseListner listner) {
        Call<ResponseActionApproval> getMarkAttendanceActionApproval = apiCall2.getMarkAttendanceActionApproval(accessToken, LvDescription, LeaveAppID, Status, Type);
        getMarkAttendanceActionApproval.enqueue(new ApiCallBack(listner, RequestCode.ACTIONAPPROVALMARKATTENDANCE));
        return getMarkAttendanceActionApproval;
    }

    public Call<?> doRequestShiftChangeDetails(String accessToken, String shiftcahngeid, OnApiResponseListner listner) {
        Call<ResponseRequestedShiftChange> doRequestShiftChangeDetails = apiCall.doRequestShiftChangeDetails(accessToken, shiftcahngeid);
        doRequestShiftChangeDetails.enqueue(new ApiCallBack(listner, RequestCode.REQUESTSHIFTCHANGEDEATIL));
        return doRequestShiftChangeDetails;
    }

    public Call<?> doRequestAttendanceCorrectionDetail(String accessToken, String attendancecorrectionid, OnApiResponseListner listner) {
        Call<ResponseRequestCorrection> doRequestAttendanceCorrectionDetail = apiCall.doRequestAttendanceCorrectionDetail(accessToken, attendancecorrectionid);
        doRequestAttendanceCorrectionDetail.enqueue(new ApiCallBack(listner, RequestCode.REQUESTATTENDANCECORRECTIONDEATIL));
        return doRequestAttendanceCorrectionDetail;
    }

    public Call<?> doRequestRegularisationDetail(String accessToken, String regularisationid, OnApiResponseListner listner) {
        Call<ResponseRequestRegularisation> doRequestRegularisationDetail = apiCall.doRequestRegularisationDetail(accessToken, regularisationid);
        doRequestRegularisationDetail.enqueue(new ApiCallBack(listner, RequestCode.REQUESTREGULARISATIONDEATIL));
        return doRequestRegularisationDetail;
    }

    public Call<?> doRequestMarkAttendanceDetail(String accessToken, String gpsattendanceid, OnApiResponseListner listner) {
        Call<ResponseRequestPunch> doRequestMarkAttendanceDetail = apiCall.doRequestMarkAttendanceDetail(accessToken, gpsattendanceid);
        doRequestMarkAttendanceDetail.enqueue(new ApiCallBack(listner, RequestCode.REQUESTMARKATTENDANCEDEATIL));
        return doRequestMarkAttendanceDetail;
    }

    public Call<?> doGetCompanyDirectory(String accessToken,
                                         int pageNo, int limit, OnApiResponseListner listner) {
        Call<List<ResponseCompanyDirectory>> doGetCompanyDirectory = apiCall.doGetDirectory(
                accessToken,
                pageNo,
                limit
        );
        doGetCompanyDirectory.enqueue(new ApiCallBack(listner, RequestCode.COMPANYDIRECTORY));
        return doGetCompanyDirectory;
    }

    public Call<?> doGetAttendance(String accessToken, int month, int year,
                                   OnApiResponseListner listner) {
        Call<List<ResponseAttendance>> doGetAttendance = apiCall.doGetAttendance(
                accessToken,
                month,
                year
        );
        doGetAttendance.enqueue(new ApiCallBack(listner, RequestCode.ATTENDANCE));
        return doGetAttendance;
    }

    public Call<?> doGetAttendanceRequests(String accessToken, String currentLocation,
                                           String latitude, String longitude, String reason,
                                           OnApiResponseListner listner) {

        Call<ResponseRequestPunch> doGetAttendanceRequests = apiCall.doGetAttendanceRequests(
                accessToken,
                currentLocation,
                latitude,
                longitude,
                reason
        );
        doGetAttendanceRequests.enqueue(new ApiCallBack(listner, RequestCode.ATTENDANCEREQUESTS));
        return doGetAttendanceRequests;
    }

    public Call<?> doGetAttendancePhotoRequests(String accessToken, String Id, String Image,
                                                OnApiResponseListner onApiResponseListner) {
        Call<ResponseRequestPunch> doGetAttendancePhotoRequests = apiCall.doGetAttendancePhotoRequests(accessToken, getPart(Id), (Image != null ? getImage("Image", new File(Image)) : null));
        doGetAttendancePhotoRequests.enqueue(new ApiCallBack(onApiResponseListner, RequestCode.ATTENDANCEIMAGEREQUESTS));
        return doGetAttendancePhotoRequests;
    }

    private MultipartBody.Part getImage(String fieldName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(fieldName, file.getName(), requestFile);
        return body;
    }

    private RequestBody getPart(String value) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), value);
        return requestFile;
    }

    public Call<?> doGetAttanceRequested(String accessToken, OnApiResponseListner listner) {
        Call<ResponseProfile> doGetAttanceRequested = apiCall.doGetAttendanceReqested(accessToken);
        doGetAttanceRequested.enqueue(new ApiCallBack(listner, RequestCode.ATTENDANCEREQUESTED));
        return doGetAttanceRequested;
    }

    public Call<?> doGetLeave(String accessToken, OnApiResponseListner listner) {
        Call<ResponseProfile> doGetLeave = apiCall.doGetLeave(accessToken);
        doGetLeave.enqueue(new ApiCallBack(listner, RequestCode.LEAVE));
        return doGetLeave;
    }

    public Call<?> doGetShift(String accessToken, OnApiResponseListner listner) {
        Call<ResponseProfile> doGetShift = apiCall.doGetShift(accessToken);
        doGetShift.enqueue(new ApiCallBack(listner, RequestCode.SHIFT));
        return doGetShift;
    }

    public Call<?> doGetCorrection(String accessToken, OnApiResponseListner listner) {
        Call<ResponseProfile> doGetCorrection = apiCall.doGetCorrection(accessToken);
        doGetCorrection.enqueue(new ApiCallBack(listner, RequestCode.CORRECTION));
        return doGetCorrection;
    }

    public Call<?> doGetRegularisation(String accessToken, OnApiResponseListner listner) {
        Call<ResponseProfile> doGetRegularisation = apiCall.doGetRegularisation(accessToken);
        doGetRegularisation.enqueue(new ApiCallBack(listner, RequestCode.REGULARISATION));
        return doGetRegularisation;
    }

    public Call<?> doGetAppVersion(String versionCode, String deviceType, OnApiResponseListner listner) {
        Call<ResponseVersion> doGetAppVersion = apiCall.doGetAppVersion(versionCode, deviceType);
        doGetAppVersion.enqueue(new ApiCallBack(listner, RequestCode.APPVERSION));
        return doGetAppVersion;
    }

    public Call<?> doGetCorrectionCategory(String accessToken, OnApiResponseListner listner) {
        Call<List<ResponseCategory>> doGetCorrectionCategory = apiCall.doGetCorrectionCategory(accessToken);
        doGetCorrectionCategory.enqueue(new ApiCallBack(listner, RequestCode.CORRECTIONCATEGORY));
        return doGetCorrectionCategory;
    }

    public Call<?> doGetRegularisationCategory(String accessToken, OnApiResponseListner listner) {
        Call<List<ResponseRegularisationCategory>> doGetRegularisationCategory =
                apiCall.doGetRegularisationCategory(accessToken);

        doGetRegularisationCategory.enqueue(new ApiCallBack(listner, RequestCode.REGULARISATIONCATEGORY));
        return doGetRegularisationCategory;
    }

    public Call<?> doCorrectionRequest(String accessToken, String actualTime, String requestDate,
                                       int nextDayOut, String reason, String requestType, String companyID,
                                       OnApiResponseListner listner) {
        Call<ResponseResetPassword> doCorrectionRequest = apiCall.doCorrectionRequest(
                accessToken, actualTime, requestDate, nextDayOut, reason, requestType, companyID);
        doCorrectionRequest.enqueue(new ApiCallBack(listner, RequestCode.CORRECTIONREQUEST));
        return doCorrectionRequest;
    }

    public Call<?> doRegularisationRequest(String accessToken, int categoryId, String fromDate,
                                           String toDate, String reason, String companyID,
                                           OnApiResponseListner listner) {
        Call<ResponseResetPassword> doRegularisationRequest = apiCall.doRegularisationRequest(
                accessToken, categoryId, fromDate, toDate, reason, companyID);
        doRegularisationRequest.enqueue(new ApiCallBack(listner, RequestCode.REGULARISATIONREQUEST));
        return doRegularisationRequest;
    }

    public Call<?> doRegisterToken(String accessToken, String deviceToken,
                                   String deviceType, OnApiResponseListner listner) {
        Call<ResponseResetPassword> doRegisterToken = apiCall.doRegisterToken(
                accessToken,
                deviceToken,
                deviceType
        );
        doRegisterToken.enqueue(new ApiCallBack(listner, RequestCode.REGISTERTOKEN));
        return doRegisterToken;
    }

    public Call<?> doGetIsPresent(String accessToken, OnApiResponseListner listner) {
        Call<ResponseIsPresent> doRegisterToken = apiCall.doGetIspresent(accessToken);
        doRegisterToken.enqueue(new ApiCallBack(listner, RequestCode.ISPRESENT));
        return doRegisterToken;
    }

    public Call<?> getRequestCount(String accessToken, OnApiResponseListner listner) {
        Call<ResponseRequestCount> getRequestCount = apiCall2.getRequestCount(accessToken);
        getRequestCount.enqueue(new ApiCallBack(listner, RequestCode.REQUESTCOUNT));
        return getRequestCount;
    }

    public Call<?> getRequestMonthlyCount(String accessToken, int month, int year, OnApiResponseListner listner) {
        Call<ResponseRequestCount> getRequestMonthlyCount = apiCall2.getRequestMonthlyCount(accessToken, month, year);
        getRequestMonthlyCount.enqueue(new ApiCallBack(listner, RequestCode.REQUESTMONTHLYCOUNT));
        return getRequestMonthlyCount;
    }

    public Call<?> getApprovalCount(String accessToken, OnApiResponseListner listner) {
        Call<ResponseApprovalCount> getApprovalCount = apiCall2.getApprovalCount(accessToken);
        getApprovalCount.enqueue(new ApiCallBack(listner, RequestCode.APPROVALCOUNT));
        return getApprovalCount;
    }

    public Call<?> doGetProfileOther(String accessToken, String empId, OnApiResponseListner listner) {
        Call<ResponseProfile> doGetProfileOther = apiCall.doGetProfileOther(accessToken, empId);
        doGetProfileOther.enqueue(new ApiCallBack(listner, RequestCode.GETPROFILEOTHERS));
        return doGetProfileOther;
    }

    public Call<?> doUpdateProfile(String authorization,
                                   String firstName,
                                   String middleName,
                                   String lastName,
                                   String mobile,
                                   String phone,
                                   String email,
                                   String gender,
                                   String bloodGroup,
                                   String city,
                                   String state,
                                   String country, String link, OnApiResponseListner listner) {
        Call<ResponseProfile> doUpdateProfile = apiCall.doUpdateProfile(
                authorization,
                firstName,
                middleName,
                lastName,
                mobile,
                phone,
                email,
                gender,
                bloodGroup,
                city,
                state,
                country,
                link
        );
        doUpdateProfile.enqueue(new ApiCallBack(listner, RequestCode.UPDATEPROFILE));
        return doUpdateProfile;
    }

    public Call<?> doUpdateProfileImage(String accessToken,
                                        String imagePath, OnApiResponseListner listner) {
        Call<ResponseResetPassword> doGetProfileOther = apiCall.doUpdateProfileImage(
                accessToken,
                getFile("profilePicture", new File(imagePath))
        );
        doGetProfileOther.enqueue(new ApiCallBack(listner, RequestCode.UPDATEPROFILEIMAGE));
        return doGetProfileOther;
    }

    private MultipartBody.Part getFile(String fieldName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                fieldName,
                file.getName(),
                requestFile
        );
        return body;
    }

    public Call<?> doSearchEmployee(String accessToken, String keyword, OnApiResponseListner listner) {
        Call<List<ResponseCompanyDirectory>> doSearchEmployee = apiCall.doSearchEmployee(
                accessToken,
                keyword
        );
        doSearchEmployee.enqueue(new ApiCallBack(listner, RequestCode.SEARCHEMPLOYEE));
        return doSearchEmployee;
    }

    public Call<?> doGetRequestedLeave(String accessToken, int month,
                                       int year, OnApiResponseListner listner) {
        Call<List<ResponseRequestLeave>> doGetRequestedLeave = apiCall.doGetRequestedLeave(accessToken, month, year);
        doGetRequestedLeave.enqueue(new ApiCallBack(listner, RequestCode.REQUESTLEAVE));
        return doGetRequestedLeave;
    }

    public Call<?> doGetRequestedCorrection(String accessToken, int month, int year, OnApiResponseListner listner) {
        Call<List<ResponseRequestCorrection>> doGetRequestedCorrection = apiCall.doGetRequestedCorrection(accessToken, month, year);
        doGetRequestedCorrection.enqueue(new ApiCallBack(listner, RequestCode.REQUESTCORRECTION));
        return doGetRequestedCorrection;
    }

    public Call<?> doGetRequestedShiftChange(String accessToken, int month, int year, OnApiResponseListner listner) {
        Call<List<ResponseRequestedShiftChange>> doGetRequestedShiftChange = apiCall.doGetRequestedShiftChange(accessToken, month, year);
        doGetRequestedShiftChange.enqueue(new ApiCallBack(listner, RequestCode.REQUESTSHIFTCHANGE));
        return doGetRequestedShiftChange;
    }

    public Call<?> doGetRequestedRegularisation(String accessToken, int month, int year, OnApiResponseListner listner) {
        Call<List<ResponseRequestRegularisation>> doGetRequestedRegularisation = apiCall.doGetRequestedRegularisation(accessToken, month, year);
        doGetRequestedRegularisation.enqueue(new ApiCallBack(listner, RequestCode.REQUESTREGULARISATION));
        return doGetRequestedRegularisation;
    }

    public Call<?> doGetRequestedAttendance(String accessToken, int month, int year, OnApiResponseListner listner) {
        Call<List<ResponseRequestAttendance>> doGetRequestedRegularisation = apiCall.doGetRequestedAttendance(accessToken, month, year);
        doGetRequestedRegularisation.enqueue(new ApiCallBack(listner, RequestCode.REQUESTATTENDANCE));
        return doGetRequestedRegularisation;
    }

    public Call<?> doGetApprovalLeaveList(String accessToken, int pageNo, int limit, OnApiResponseListner listner) {
        Call<ResponseApprovalLeave> doGetApprovalLeaveList = apiCall.doGetApprovalLeaveList(accessToken, pageNo, limit);
        doGetApprovalLeaveList.enqueue(new ApiCallBack(listner, RequestCode.APPROVALLEAVE));
        return doGetApprovalLeaveList;
    }

    public Call<?> doGetApprovalShiftList(String accessToken, int pageNo, int limit, OnApiResponseListner listner) {
        Call<ResponseApprovalShiftChange> doGetApprovalShiftList = apiCall.doGetApprovalShiftList(accessToken, pageNo, limit);
        doGetApprovalShiftList.enqueue(new ApiCallBack(listner, RequestCode.APPROVALSHIFT));
        return doGetApprovalShiftList;
    }

    public Call<?> doGetApprovalCorrectionList(String accessToken, int pageNo, int limit, OnApiResponseListner listner) {
        Call<ResponseApprovalAttendanceCorrection> doGetApprovalCorrectionList = apiCall.doGetApprovalCorrectionList(accessToken, pageNo, limit);
        doGetApprovalCorrectionList.enqueue(new ApiCallBack(listner, RequestCode.APPROVALCORRECTION));
        return doGetApprovalCorrectionList;
    }

    public Call<?> doGetApprovalRegularisationList(String accessToken, int pageNo, int limit, OnApiResponseListner listner) {
        Call<ResponseApprovalRegularizationAttendance> doGetApprovalRegularisationList = apiCall.doGetApprovalRegularisationList(accessToken, pageNo, limit);
        doGetApprovalRegularisationList.enqueue(new ApiCallBack(listner, RequestCode.APPROVALREGULARISATION));
        return doGetApprovalRegularisationList;
    }

    public Call<?> doGetApprovalMarkAttendanceList(String accessToken, int pageNo, int limit, OnApiResponseListner listner) {
        Call<ResponseApprovalMarkAttendance> doGetApprovalMarkAttendanceList = apiCall2.doGetApprovalMarkAttendanceList(accessToken, pageNo, limit);
        doGetApprovalMarkAttendanceList.enqueue(new ApiCallBack(listner, RequestCode.APPROVALMARKATTENDANCE));
        return doGetApprovalMarkAttendanceList;
    }

    public Call<?> doGetAllRequest(String accessToken, int month, int year, OnApiResponseListner listner) {
        Call<ResponseRequest> doGetAllRequest = apiCall2.doGetAllRequest(accessToken, month, year);
        doGetAllRequest.enqueue(new ApiCallBack(listner, RequestCode.REQUESTALL));
        return doGetAllRequest;
    }

    public Call<?> doGetLeaveDetailsCount(String accessToken, Integer year, OnApiResponseListner listner) {
        Call<ResponseLeaveDetails> doGetLeaveDetails = apiCall2.doGetLeaveDetailsCount(accessToken, year);
        doGetLeaveDetails.enqueue(new ApiCallBack(listner, RequestCode.LEAVEDETAILSCOUNT));
        return doGetLeaveDetails;
    }

    public Call<?> doGetAnnualLeaveCount(String accessToken, OnApiResponseListner listner) {
        Call<ResponseAnnualLeave> doGetAnnualLeave = apiCall.doGetAnnualLeaveCount(accessToken);
        doGetAnnualLeave.enqueue(new ApiCallBack(listner, RequestCode.ANNUALLEAVE));
        return doGetAnnualLeave;
    }

    public Call<?> doApplyLeave(String accessToken,
                                LeaveDetails leaveDetails,
                                OnApiResponseListner listner) {
        Call<ResponseResetPassword> doApplyLeave = apiCall.doApplyLeave(accessToken, leaveDetails);
        doApplyLeave.enqueue(new ApiCallBack(listner, RequestCode.APPLYLEAVE));
        return doApplyLeave;
    }

    public Call<?> doGetShiftChangeList(String accessToken,
                                        Integer year,
                                        OnApiResponseListner listner) {
        Call<List<ResponseShiftChange>> doGetShiftChangeList = apiCall.doGetShiftChangeList(
                accessToken,
                year);
        doGetShiftChangeList.enqueue(new ApiCallBack(listner, RequestCode.SHIFTCHANGELIST));
        return doGetShiftChangeList;
    }

    public Call<?> doGetShiftAvailable(String accessToken, OnApiResponseListner listner) {
        Call<List<ResponseShiftAvailable>> doGetShiftAvailable = apiCall.doGetShiftAvailable(
                accessToken
        );
        doGetShiftAvailable.enqueue(new ApiCallBack(listner, RequestCode.SHIFTAVAILABLE));
        return doGetShiftAvailable;
    }

    public Call<?> doGetClaimList(String accessToken, OnApiResponseListner listner) {
        Call<ResponseClaimList> doGetClaimList = apiCall2.doGetClaimList(accessToken);
        doGetClaimList.enqueue(new ApiCallBack(listner, RequestCode.CLAIMLIST));
        return doGetClaimList;
    }

    public Call<?> doGetShiftUpdate(String accessToken, String shiftId,
                                    String fromDate,
                                    String todate, String reason,
                                    String companyId,
                                    OnApiResponseListner listner) {
        Call<ResponseResetPassword> doGetShiftUpdate = apiCall.doUpdateShift(
                accessToken,
                shiftId,
                fromDate,
                todate,
                reason,
                companyId
        );
        doGetShiftUpdate.enqueue(new ApiCallBack(listner, RequestCode.SHIFTUPDATE));
        return doGetShiftUpdate;
    }


    public Call<?> addClaimDetails(String accessToken,
                                   AddClaimDetails claimDetails,
                                   OnApiResponseListner listner) {
        Call<ClaimExpenseResponse> doApplyLeave = apiCall2.addClaimDetails(accessToken, claimDetails);
        doApplyLeave.enqueue(new ApiCallBack(listner, RequestCode.ADDCLAIMDETAILS));
        return doApplyLeave;
    }


    public Call<?> getModeOfTravelDetails(String accessToken, OnApiResponseListner listner) {
        Call<ModeOfTravel> modeoftravel = apiCall2.doRequestModeOfTravel(accessToken);
        modeoftravel.enqueue(new ApiCallBack(listner, RequestCode.MODEOFTRAVEL));
        return modeoftravel;
    }


    public Call<?> getExpenseDetailList(String accessToken, String CityID, OnApiResponseListner listner) {
        Call<ExpenseList> expenseListCall = apiCall2.doRequestExpenseList(accessToken, CityID);
        expenseListCall.enqueue(new ApiCallBack(listner, RequestCode.EXPENSELIST));
        return expenseListCall;
    }


    public Call<?> uploadAttachmentList(String accessToken, RequestBody Id, MultipartBody.Part image, OnApiResponseListner listner) {
        Call<ResponseBody> uploadattachmentcall = apiCall2.uploadClaimAttachmentImage(accessToken, Id, image);
        uploadattachmentcall.enqueue(new ApiCallBack(listner, RequestCode.UPLOADCLAIMATTACHMENTIMAGE));
        return uploadattachmentcall;
    }

    public Call<?> getApprovalExpenseClaimList(String accessToken,
                                               int pageNo, int limit, OnApiResponseListner listner) {
        Call<ResponseApprovalExpenseClaim> getApprovalClaimList = apiCall2.getApprovalExpenseClaimList(
                accessToken,
                pageNo,
                limit
        );
        getApprovalClaimList.enqueue(new ApiCallBack(listner, RequestCode.APPROVAL_EXPENSE_CLAIM));
        return getApprovalClaimList;
    }

    public Call<?> getActionExpenseClaim(String accessToken,
                                         ActionExpenseClaim expenseClaim, OnApiResponseListner listner) {
        Call<ResponseActionExpenseClaim> getApprovalClaimList = apiCall2.getActionExpenseCLaim(
                accessToken, expenseClaim);
        getApprovalClaimList.enqueue(new ApiCallBack(listner, RequestCode.ACTION_EXPENSE_CLAIM));
        return getApprovalClaimList;
    }
}