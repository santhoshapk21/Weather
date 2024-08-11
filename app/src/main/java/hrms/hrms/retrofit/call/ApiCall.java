package hrms.hrms.retrofit.call;


import hrms.hrms.model.AddClaimDetails;
import hrms.hrms.model.ClaimExpenseResponse;
import hrms.hrms.model.ExpenseList;
import hrms.hrms.model.LeaveDetails;
import hrms.hrms.model.ModeOfTravel;
import hrms.hrms.retrofit.WebAPI;
import hrms.hrms.retrofit.model.Leave.AnnualLeave.ResponseAnnualLeave;
import hrms.hrms.retrofit.model.Leave.LeaveDetails.ResponseLeaveDetails;
import hrms.hrms.retrofit.model.approvals.leave.ActionExpenseClaim;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionExpenseClaim;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalAttendanceCorrection;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalCount;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalExpenseClaim;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalLeave;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalLeaveDetail;
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
import hrms.hrms.retrofit.model.request.leave.ResponseLeaveDetail;
import hrms.hrms.retrofit.model.request.leave.ResponseRequestLeave;
import hrms.hrms.retrofit.model.request.regularisation.ResponseRequestRegularisation;
import hrms.hrms.retrofit.model.request.shiftChange.ResponseRequestedShiftChange;
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;
import hrms.hrms.retrofit.model.shiftChange.ResponseShiftAvailable;
import hrms.hrms.retrofit.model.shiftChange.ResponseShiftChange;
import hrms.hrms.retrofit.model.versionUpdate.ResponseVersion;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by yudizsolutions on 11/04/16.
 */
public interface ApiCall {

    @FormUrlEncoded
    @POST(WebAPI.LOGIN)
    Call<ResponseLogin> doLogin(
            @Field("Domain") String Domain,
            @Field("Username") String Username,
            @Field("Password") String Password
    );

    @GET(WebAPI.LOGOUT)
    Call<ResponseLogin> doLogout(
            @Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST(WebAPI.RESETPASSWORD)
    Call<ResponseResetPassword> doResetPassword(
            @Field("Domain") String Domain,
            @Field("Username") String Username);


    @GET(WebAPI.PROFILE)
    Call<ResponseProfile> doGetProfile(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.COMPANYDIRECTORY)
    Call<List<ResponseCompanyDirectory>> doGetDirectory(
            @Header("Authorization") String Authorization,
            @Field("PageNo") Integer PageNo,
            @Field("Limit") Integer Limit
    );


    @FormUrlEncoded
    @POST(WebAPI.ATTENDANCE)
    Call<List<ResponseAttendance>> doGetAttendance(
            @Header("Authorization") String Authorization,
            @Field("Month") Integer Month,
            @Field("Year") Integer Year);


    @GET(WebAPI.ATTENDANCEREQUESTED)
    Call<ResponseProfile> doGetAttendanceReqested(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.ATTENDANCEREQUESTS)
    Call<ResponseRequestPunch> doGetAttendanceRequests(
            @Header("Authorization") String Authorization,
            @Field("CurrentLocation") String CurrentLocation,
            @Field("Latitude") String Latitude,
            @Field("Longitude") String Longitude,
            @Field("Reason") String Reason
    );

    @Multipart
    @POST(WebAPI.ATTENDANCEPHOTOREQUESTS)
    Call<ResponseRequestPunch> doGetAttendancePhotoRequests(
            @Header("Authorization") String Authorization,
            @Part("Id") RequestBody Id,
            @Part MultipartBody.Part Image
    );

    @FormUrlEncoded
    @POST(WebAPI.APPVERSION)
    Call<ResponseVersion> doGetAppVersion(
            @Field("VersionCode") String VersionCode,
            @Field("DeviceType") String DeviceType
    );

    @GET(WebAPI.LEAVE)
    Call<ResponseProfile> doGetLeave(
            @Header("Authorization") String Authorization
    );

    @GET(WebAPI.SHIFT)
    Call<ResponseProfile> doGetShift(
            @Header("Authorization") String Authorization
    );

    @GET(WebAPI.CORRECTION)
    Call<ResponseProfile> doGetCorrection(
            @Header("Authorization") String Authorization
    );

    @GET(WebAPI.REGULARISATION)
    Call<ResponseProfile> doGetRegularisation(
            @Header("Authorization") String Authorization
    );

    @GET(WebAPI.CORRECTIONCATEGORY)
    Call<List<ResponseCategory>> doGetCorrectionCategory(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.CORRECTIONREQUEST)
    Call<ResponseResetPassword> doCorrectionRequest(
            @Header("Authorization") String Authorization,
            @Field("ActualTime") String ActualTime,
            @Field("RequestDate") String RequestDate,
            @Field("NextDayOut") Integer NextDayOut,
            @Field("Reason") String Reason,
            @Field("RequestType") String RequestType,
            @Field("CompanyID") String companyID
    );

    @GET(WebAPI.REGULARISATIONCATEGORY)
    Call<List<ResponseRegularisationCategory>> doGetRegularisationCategory(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.REGULARISATIONREQUEST)
    Call<ResponseResetPassword> doRegularisationRequest(
            @Header("Authorization") String Authorization,
            @Field("CategoryId") Integer CategoryId,
            @Field("FromDate") String FromDate,
            @Field("ToDate") String ToDate,
            @Field("Reason") String Reason,
            @Field("CompanyID") String companyID
    );

    @FormUrlEncoded
    @POST(WebAPI.REGISTERTOKEN)
    Call<ResponseResetPassword> doRegisterToken(
            @Header("Authorization") String Authorization,
            @Field("DeviceToken") String DeviceToken,
            @Field("DeviceType") String DeviceType
    );

    @GET(WebAPI.ISPRESENT)
    Call<ResponseIsPresent> doGetIspresent(
            @Header("Authorization") String Authorization
    );

    @GET(WebAPI.REQUESTCOUNT)
    Call<ResponseRequestCount> getRequestCount(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.REQUESTMONTHLYCOUNT)
    Call<ResponseRequestCount> getRequestMonthlyCount(
            @Header("Authorization") String Authorization,
            @Field("Month") int Month,
            @Field("Year") int Year
    );

    @GET(WebAPI.APPROVALCOUNT)
    Call<ResponseApprovalCount> getApprovalCount(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.UPDATEPROFILE)
    Call<ResponseProfile> doUpdateProfile(
            @Header("Authorization") String Authorization,
            @Field("FirstName") String FirstName,
            @Field("MiddleName") String MiddleName,
            @Field("LastName") String LastName,
            @Field("Mobile") String Mobile,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("Gender") String Gender,
            @Field("BloodGroup") String BloodGroup,
            @Field("City") String City,
            @Field("State") String State,
            @Field("Country") String Country,
            @Field("ProfilePicture") String ProfilePicture
    );


    @FormUrlEncoded
    @POST(WebAPI.GETPROFILEOTHERS)
    Call<ResponseProfile> doGetProfileOther(
            @Header("Authorization") String Authorization,
            @Field("EmployeeId") String EmployeeId
    );

    @Multipart
    @POST(WebAPI.CHANGEPROFILEIMAGE)
    Call<ResponseResetPassword> doUpdateProfileImage(
            @Header("Authorization") String Authorization,
            @Part MultipartBody.Part profilePicture
    );

    @GET(WebAPI.SEARCHDIRECTORY)
    Call<List<ResponseCompanyDirectory>> doSearchEmployee(
            @Header("Authorization") String Authorization,
            @Query("Keyword") String Keyword
    );

    @FormUrlEncoded
    @POST(WebAPI.REQUESTALL)
    Call<ResponseRequest> doGetAllRequest(
            @Header("Authorization") String Authorization,
            @Field("Month") Integer Month,
            @Field("Year") Integer Year
    );

    @FormUrlEncoded
    @POST(WebAPI.REQUESTLEAVE)
    Call<List<ResponseRequestLeave>> doGetRequestedLeave(
            @Header("Authorization") String Authorization,
            @Field("Month") Integer Month,
            @Field("Year") Integer Year
    );

    @FormUrlEncoded
    @POST(WebAPI.REQUESTREGULARISATION)
    Call<List<ResponseRequestRegularisation>> doGetRequestedRegularisation(
            @Header("Authorization") String Authorization,
            @Field("Month") Integer Month,
            @Field("Year") Integer Year
    );

    @FormUrlEncoded
    @POST(WebAPI.REQUESTCORRECTION)
    Call<List<ResponseRequestCorrection>> doGetRequestedCorrection(
            @Header("Authorization") String Authorization,
            @Field("Month") Integer Month,
            @Field("Year") Integer Year
    );

    @FormUrlEncoded
    @POST(WebAPI.REQUESTSHIFTCHANGE)
    Call<List<ResponseRequestedShiftChange>> doGetRequestedShiftChange(
            @Header("Authorization") String Authorization,
            @Field("Month") Integer Month,
            @Field("Year") Integer Year
    );

    @FormUrlEncoded
    @POST(WebAPI.REQUESTATTENDANCE)
    Call<List<ResponseRequestAttendance>> doGetRequestedAttendance(
            @Header("Authorization") String Authorization,
            @Field("Month") Integer Month,
            @Field("Year") Integer Year
    );

    @FormUrlEncoded
    @POST(WebAPI.APPROVALLEAVE)
    Call<ResponseApprovalLeave> doGetApprovalLeaveList(
            @Header("Authorization") String Authorization,
            @Field("PageNo") Integer PageNo,
            @Field("Limit") Integer Limit
    );

    @FormUrlEncoded
    @POST(WebAPI.APPROVALSHIFT)
    Call<ResponseApprovalShiftChange> doGetApprovalShiftList(
            @Header("Authorization") String Authorization,
            @Field("PageNo") Integer PageNo,
            @Field("Limit") Integer Limit
    );

    @FormUrlEncoded
    @POST(WebAPI.APPROVALCORRECTION)
    Call<ResponseApprovalAttendanceCorrection> doGetApprovalCorrectionList(
            @Header("Authorization") String Authorization,
            @Field("PageNo") Integer PageNo,
            @Field("Limit") Integer Limit
    );

    @FormUrlEncoded
    @POST(WebAPI.APPROVALREGULARISATION)
    Call<ResponseApprovalRegularizationAttendance> doGetApprovalRegularisationList(
            @Header("Authorization") String Authorization,
            @Field("PageNo") Integer PageNo,
            @Field("Limit") Integer Limit
    );

    @FormUrlEncoded
    @POST(WebAPI.APPROVALMARKATTENDANCE)
    Call<ResponseApprovalMarkAttendance> doGetApprovalMarkAttendanceList(
            @Header("Authorization") String Authorization,
            @Field("PageNo") Integer PageNo,
            @Field("Limit") Integer Limit
    );

    @GET(WebAPI.ANNUALLEAVE)
    Call<ResponseAnnualLeave> doGetAnnualLeaveCount(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.LEAVEDETAILS)
    Call<ResponseLeaveDetails> doGetLeaveDetailsCount(
            @Header("Authorization") String Authorization,
            @Field("Year") Integer Year
    );

    @POST(WebAPI.APPLYLEAVE)
    Call<ResponseResetPassword> doApplyLeave(
            @Header("Authorization") String Authorization,
            @Body LeaveDetails dates
    );


    @FormUrlEncoded
    @POST(WebAPI.SHIFTCHANGELIST)
    Call<List<ResponseShiftChange>> doGetShiftChangeList(
            @Header("Authorization") String Authorization,
            @Field("Year") Integer Year
    );

    @GET(WebAPI.SHIFTAVAILABLE)
    Call<List<ResponseShiftAvailable>> doGetShiftAvailable(
            @Header("Authorization") String Authorization
    );

    @GET(WebAPI.CLAIMLIST)
    Call<ResponseClaimList> doGetClaimList(
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST(WebAPI.SHIFTUPDATE)
    Call<ResponseResetPassword> doUpdateShift(
            @Header("Authorization") String Authorization,
            @Field("ShiftId") String ShiftId,
            @Field("FromDate") String FromDate,
            @Field("ToDate") String ToDate,
            @Field("Reason") String Reason,
            @Field("CompanyID") String CompanyID
    );

    @FormUrlEncoded
    @POST(WebAPI.LEAVECANCEL)
    Call<ResponseLeaveCancel> getRequestLeaveCancel(
            @Header("Authorization") String Authorization,
            @Field("Description") String LvDescription,
            @Field("LeaveAppID") String Id);

    @FormUrlEncoded
    @POST(WebAPI.ACTIONAPPROVAL)
    Call<ResponseActionApproval> getActionApproval(
            @Header("Authorization") String Authorization,
            @Field("Comment") String LvDescription,
            @Field("Id") String LeaveAppID,
            @Field("Status") String Status,
            @Field("Type") String Type);

    @FormUrlEncoded
    @POST(WebAPI.ACTIONAPPROVALMARKATTENDANCE)
    Call<ResponseActionApproval> getMarkAttendanceActionApproval(
            @Header("Authorization") String Authorization,
            @Field("ApprovalReason") String LvDescription,
            @Field("GPSAttendanceID") String LeaveAppID,
            @Field("ReportingStatus") String Status,
            @Field("FinalStatus") String Type);

    @GET(WebAPI.LEAVEDETAIL)
    Call<ResponseLeaveDetail> doRequestLeaveDetails(
            @Header("Authorization") String Authorization,
            @Query("LeaveCode") String LeaveCode);

    @GET(WebAPI.LEAVEDETAIL)
    Call<ResponseApprovalLeaveDetail> doRequestLeaveDetail(
            @Header("Authorization") String Authorization,
            @Query("LeaveCode") String LeaveCode);

    @GET(WebAPI.SHIFTCHANGEDETAIL)
    Call<ResponseRequestedShiftChange> doRequestShiftChangeDetails(
            @Header("Authorization") String accessToken,
            @Query("Shiftreq_id") String shiftcahngeid);

    @GET(WebAPI.ATTENDANCECORRECTIONDETAIL)
    Call<ResponseRequestCorrection> doRequestAttendanceCorrectionDetail(
            @Header("Authorization") String accessToken,
            @Query("ar_id") String attendancecorrectionid);

    @GET(WebAPI.REGULARISATIONDETAIL)
    Call<ResponseRequestRegularisation> doRequestRegularisationDetail(
            @Header("Authorization") String accessToken,
            @Query("reg_id") String regularisationid);

    @GET(WebAPI.MARKATTENDACNEDETAIL)
    Call<ResponseRequestPunch> doRequestMarkAttendanceDetail(
            @Header("Authorization") String accessToken,
            @Query("attendance_id") String gpsattendanceid);


    @POST(WebAPI.ADDCLAIMDETAIL)
    Call<ClaimExpenseResponse> addClaimDetails(
            @Header("Authorization") String Authorization,
            @Body AddClaimDetails ClaimDetails
    );


    @GET(WebAPI.GETMODEOFTRAVEL)
    Call<ModeOfTravel> doRequestModeOfTravel(
            @Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST(WebAPI.GETEXPENSELIST)
    Call<ExpenseList> doRequestExpenseList(
            @Header("Authorization") String Authorization, @Field("CityID") String CityID);


    @Multipart
    @POST(WebAPI.UPLOADCLAIMATTACHMENTLIST)
    Call<ResponseBody> uploadClaimAttachmentImage(
            @Header("Authorization") String Authorization,
            @Part("Id") RequestBody Id,
            @Part MultipartBody.Part Image
    );

    @FormUrlEncoded
    @POST(WebAPI.APPROVAL_EXPENSE_CLAIM_LIST)
    Call<ResponseApprovalExpenseClaim> getApprovalExpenseClaimList(
            @Header("Authorization") String Authorization,
            @Field("PageNo") Integer PageNo,
            @Field("Limit") Integer Limit
    );

    @FormUrlEncoded
    @POST(WebAPI.ACTION_EXPENSE_CLAIM)
    Call<ResponseActionExpenseClaim> getActionExpenseCLaim(
            @Header("Authorization") String Authorization,
            @Body ActionExpenseClaim expenseClaim
    );
}