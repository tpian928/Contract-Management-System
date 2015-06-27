package obj;

import java.util.HashSet;
import java.util.Set;

import jdbc.Contract;
import jdbc.Process;
import jdbc.Role;
import jdbc.UserJDBCAction;


public class User {
	private String id;
	private String access_taken;
	private Set<String> roleNameSet;
	private String name;

	
	public User(String id,String access_taken) {
		this.id=id;
		this.access_taken=access_taken;
		this.name=UserJDBCAction.getUserById(id).getName();
	}
	
	public User() {
		
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
	
	/**
	 * 判断用户是否有某项功能
	 * @param func_id
	 * @return
	 */
	public boolean hasFunc(int func_id) {
		return Role.hasThisFunc(id, func_id);
	}
	
	public Set<Integer> getRoleIds() {
		Set<Integer> rold_idSet = new HashSet<Integer>();
		return rold_idSet;
	}

	public Set<String> getRoleNameSet() {
		return roleNameSet;
	}

	public void setRoleNameSet(Set<String> roleNameSet) {
		this.roleNameSet = roleNameSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void resetUsersRole(int userid,Set<Integer> roleidSet) {
		UserJDBCAction mAction = new UserJDBCAction();
		mAction.resetUsersRole(userid, roleidSet);
	}
	
	/**
	 * 得到具有某项功能的所有用户
	 * @param funcid 功能ID
	 * @return
	 */
	public Set<User> getAllUserWithFunc(int funcid) {
		Set<User> userSet = new HashSet<User>();
		
		Set<User> allUserSet = UserJDBCAction.getUserByName("");
		
		for(User tmp:allUserSet){
			if (Role.hasThisFunc(tmp.getId(), funcid)) {
				userSet.add(tmp);
			}
		}
		
		return userSet;
	}
	
	/**
	 * 
	 * @param type 类型 0-会签 1-审批 2-签订
	 * @param state 状态 0-未完成 1-已完成 2-已否决
	 * @return
	 */
	public Set<Contract> getContracts(int type,int state,String q) {
		System.out.println("getContracts");
		Set<Contract> cSet = new HashSet<Contract>();
		Contract mContract = new Contract();
		
		cSet = mContract.getContractSetWithTSU(type, state, this.name,q);		
		return cSet;
	}
	
	
	/**
	 * 得到需要定稿的合同
	 * @return 需要该用户定稿的合同
	 */
	public Set<Contract> getDingGao() {
		//状态是2，即会签完成
		Set<Contract> resultSet = new HashSet<Contract>();
		Contract mContract = new Contract();
		Set<Contract> orginalSet = mContract.getContractsByState(2);

		for(Contract tmp:orginalSet){
			if (tmp.getDraftmanname().equals(this.getName())) {
				resultSet.add(tmp);
			}
		}
		return resultSet;
	}
	
	public Set<Contract> getYiDingGao() {
		//状态是2，即会签完成
		Set<Contract> resultSet = new HashSet<Contract>();
		Contract mContract = new Contract();
		Set<Contract> orginalSet = mContract.getContractsByState(3);
		
		for(Contract tmp:orginalSet){
			if (tmp.getDraftmanname().equals(this.getName())) {
				resultSet.add(tmp);
			}
		}
		return resultSet;
	}
	
	
		
	public void completeQH(Integer cid,String content) {
		Process mProcess = new Process(cid, 0, 1, this.getName(), content);
		Contract mContract = new Contract(cid);
		mContract.updateProcess(mProcess);
		
		if (mContract.havaCompleteHQ()) {
			mContract.setState(2);
		}
		
	}
	
	/**
	 * 完成审批
	 * @param cid 合同ID
	 * @param content 内容
	 * @param agree 同意与否
	 * @return
	 */
	public boolean completeSP(Integer cid,String content,boolean agree) {

		boolean result = false;
		
		int agreeInt = 0;
		if (agree) {
			agreeInt=1;
		}
		else {
			agreeInt=2;
		}
		
		Process mProcess = new Process(cid, 1, agreeInt, this.getName(), content);
		Contract mContract = new Contract(cid);
		result = mContract.updateProcess(mProcess);
		
		if (mContract.havaCompleteSP()) {
			mContract.setState(4);
		}
		return result;
	}
	
	/**
	 * 完成签订
	 * @param cid 合同id
	 * @param content 内容
	 * @return
	 */
	public boolean completeQD(Integer cid,String content) {
		boolean result = false;
		Process mProcess = new Process(cid, 2, 1, this.getName(), content);
		Contract mContract = new Contract(cid);
		result = mContract.updateProcess(mProcess);
		
		if (mContract.havaCompleteQD()) {
			mContract.setState(5);
		}
		return result;
	}

		
}
