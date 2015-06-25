package jdbc;

public class Process {
	
	private int cid,type,state;
	private String username,content,time;
	
	/**
	 * 合同进程的构造方法
	 * @param cid 合同id
	 * @param type 类型 0-会签 1-审批 2-签订
	 * @param state 0－未完成 1-已完成 2-已否决
	 * @param username 操作人的名字
	 * @param content 语句
	 */
	public Process(int cid, int type, int state, String username,
			String content) {
		
		this.cid = cid;
		this.type = type;
		this.state = state;
		this.username = username;
		this.content = content;

	}
		
	public Process(){
		
	}
	
	//以下是get，set
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
