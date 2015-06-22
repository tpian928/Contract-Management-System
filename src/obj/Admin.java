package obj;

import jdbc.Contract;
import jdbc.Process;

public class Admin extends User {

	public Admin(String id,String access_taken){
		super(id, access_taken);
		
	}
	
	/**
	 * 管理员分配合同
	 * @param type
	 */
	public void arrange(Integer cid,int type,String username) {
		Process mProcess = new Process(cid, type, 0, username, "");
		Contract mContract = new Contract(cid);
		mContract.setProcess(mProcess);
	}
	
}
