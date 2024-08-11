package hrms.hrms.retrofit.model.companyDirectory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class ResponseCompanyDirectory {

    @SerializedName("Designation")
    @Expose
    private String designation;

    @SerializedName("EmailId")
    @Expose
    private String emailId;

    @SerializedName("FirstName")
    @Expose
    private String firstName;

    @SerializedName("ProfileImage")
    @Expose
    private String profileImage;

    @SerializedName("LastName")
    @Expose
    private String lastName;

    @SerializedName("IsPresent")
    @Expose
    private String isPresent;

    @SerializedName("EmployeeId")
    @Expose
    private String employeeId;

    @SerializedName("MiddleName")
    @Expose
    private String middleName;


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() {
        return String.format(Locale.getDefault(), "%s %s", firstName, lastName);
    }
}