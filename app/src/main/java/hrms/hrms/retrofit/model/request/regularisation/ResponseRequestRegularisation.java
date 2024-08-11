package hrms.hrms.retrofit.model.request.regularisation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRequestRegularisation{

	@SerializedName("CategoryId")
	@Expose
	private String categoryId;

	@SerializedName("Status")
	@Expose
	private String status;

	@SerializedName("RegularisationId")
	@Expose
	private String regularisationId;

	@SerializedName("Description")
	@Expose
	private String description;

	@SerializedName("EmaployeeId")
	@Expose
	private String emaployeeId;

	@SerializedName("CategoryName")
	@Expose
	private String categoryName;

	@SerializedName("FromDate")
	@Expose
	private String fromDate;

	@SerializedName("ToDate")
	@Expose
	private String toDate;

	@SerializedName("ManagerReason")
	@Expose
	private String comment;

	@SerializedName("Name")
	@Expose
	private String name;

	public String ReportPerson1Id;
	public String ReportPerson1Name;
	public String ReportPerson2Id;
	public String ReportPerson2Name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setRegularisationId(String regularisationId){
		this.regularisationId = regularisationId;
	}

	public String getRegularisationId(){
		return regularisationId;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setEmaployeeId(String emaployeeId){
		this.emaployeeId = emaployeeId;
	}

	public String getEmaployeeId(){
		return emaployeeId;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setFromDate(String fromDate){
		this.fromDate = fromDate;
	}

	public String getFromDate(){

		return fromDate;
	}

	public void setToDate(String toDate){
		this.toDate = toDate;
	}

	public String getToDate(){

		return toDate;
	}
}