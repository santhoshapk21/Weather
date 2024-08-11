package hrms.hrms.retrofit.model.regularisationCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRegularisationCategory{

	@SerializedName("RegularisationId")
	@Expose
	private String regularisationId;

	@SerializedName("RegularisationCategory")
	@Expose
	private String regularisationCategory;

	public void setRegularisationId(String regularisationId){
		this.regularisationId = regularisationId;
	}

	public String getRegularisationId(){
		return regularisationId;
	}

	public void setRegularisationCategory(String regularisationCategory){
		this.regularisationCategory = regularisationCategory;
	}

	public String getRegularisationCategory(){
		return regularisationCategory;
	}
}