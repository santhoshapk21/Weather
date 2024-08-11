package hrms.hrms.retrofit.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCategory{

	@SerializedName("Type")
	@Expose
	private String type;

	@SerializedName("Id")
	@Expose
	private int id;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}