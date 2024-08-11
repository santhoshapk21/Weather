package hrms.hrms.retrofit.model.Leave.AnnualLeave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAnnualLeave {

	@SerializedName("Taken")
	@Expose
	private String taken;

	@SerializedName("Balance")
	@Expose
	private String balance;

	@SerializedName("PendingApproval")
	@Expose
	private String pendingApproval;

	public void setTaken(String taken){
		this.taken = taken;
	}

	public String getTaken(){
		return taken;
	}

	public void setBalance(String balance){
		this.balance = balance;
	}

	public String getBalance(){
		return balance;
	}

	public void setPendingApproval(String pendingApproval){
		this.pendingApproval = pendingApproval;
	}

	public String getPendingApproval(){
		return pendingApproval;
	}
}