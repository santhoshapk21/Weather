package hrms.hrms.retrofit.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class ResponseLogin {

    @SerializedName("Designation")
    @Expose
    private String designation;

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

    @SerializedName("CompanyName")
    @Expose
    private String companyName;

    @SerializedName("CompanyID")
    @Expose
    private String CompanyID;

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public boolean isGpsEnable() {
        return isGpsEnable;
    }

    public void setGpsEnable(boolean gpsEnable) {
        isGpsEnable = gpsEnable;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getFullName() {
        return String.format(Locale.getDefault(), "%s %s %s", firstName, middleName, lastName);
    }
}