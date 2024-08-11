package hrms.hrms.retrofit.model.resetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseResetPassword{

	@SerializedName("Message")
	@Expose
	private String message;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}