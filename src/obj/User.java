package obj;

public class User {
	private String id;
	private String access_taken;
	
	public User(String id,String access_taken) {
		this.id=id;
		this.access_taken=access_taken;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccess_taken() {
		return access_taken;
	}
	public void setAccess_taken(String access_taken) {
		this.access_taken = access_taken;
	}
}
