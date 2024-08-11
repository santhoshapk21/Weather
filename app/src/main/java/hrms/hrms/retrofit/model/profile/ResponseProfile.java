package hrms.hrms.retrofit.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Locale;


public class ResponseProfile implements Serializable {

    @SerializedName("Designation")
    @Expose
    private String designation;

    @SerializedName("CompanyID")
    @Expose
    public String companyID;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("BloodGroup")
    @Expose
    private String bloodGroup;

    @SerializedName("FirstName")
    @Expose
    private String firstName;

    @SerializedName("Gender")
    @Expose
    private String gender;

    @SerializedName("City")
    @Expose
    private String city;

    @SerializedName("MiddleName")
    @Expose
    private String middleName;

    @SerializedName("Mobile")
    @Expose
    private String mobile;

    @SerializedName("CompanyLogo")
    @Expose
    private String companyLogo;

    @SerializedName("Department")
    @Expose
    private String department;

    @SerializedName("Phone")
    @Expose
    private String phone;

    @SerializedName("State")
    @Expose
    private String state;

    @SerializedName("Country")
    @Expose
    private String country;

    @SerializedName("isManager")
    @Expose
    private boolean isManager;

    @SerializedName("IsGpsEnable")
    @Expose
    private boolean isGpsEnable;

    @SerializedName("IsGeoFancingEnable")
    @Expose
    private boolean isGeoFancingEnable;

    @SerializedName("LastName")
    @Expose
    private String lastName;

    @SerializedName("EmployeeID")
    @Expose
    private String employeeID;

    @SerializedName("ProfilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("UserToken")
    @Expose
    private String userToken;

    @SerializedName("Type")
    @Expose
    private String mType;

    @SerializedName("Value1")
    @Expose
    private String mValue1;

    @SerializedName("Value2")
    @Expose
    private String mValue2;

    @SerializedName("Value3")
    @Expose
    private String mValue3;

   /* Time Zone Added For specific country
   "ServierTime": "Asia/Calcutta",
   "LocationTime": "Asia/Calcutta"*/

    @SerializedName("ServerTime")
    @Expose
    private String ServerTime;

    @SerializedName("LocationTime")
    @Expose
    private String LocationTime;

    public boolean isGeoFancingEnable() {
        return isGeoFancingEnable;
    }

    public void setGeoFancingEnable(boolean geoFancingEnable) {
        isGeoFancingEnable = geoFancingEnable;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmValue1() {
        return mValue1;
    }

    public void setmValue1(String mValue1) {
        this.mValue1 = mValue1;
    }

    public String getmValue2() {
        return mValue2;
    }

    public void setmValue2(String mValue2) {
        this.mValue2 = mValue2;
    }

    public String getmValue3() {
        return mValue3;
    }

    public void setmValue3(String mValue3) {
        this.mValue3 = mValue3;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean isIsGpsEnable() {
        return isGpsEnable;
    }

    public void setIsGpsEnable(boolean isGpsEnable) {
        this.isGpsEnable = isGpsEnable;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getName() {
        return String.format(Locale.getDefault(), "%s %s", firstName, lastName);
    }

    public String getServerTime() {
        return ServerTime;
    }

    public void setServerTime(String serverTime) {
        ServerTime = serverTime;
    }

    public String getLocationTime() {
        return LocationTime;
    }

    public void setLocationTime(String locationTime) {
        LocationTime = locationTime;
    }
}