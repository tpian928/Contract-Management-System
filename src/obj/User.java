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
	public Set<Contract> getContracts(int type,int state) {
		System.out.println("getContracts");
		Set<Contract> cSet = new HashSet<Contract>();
		Contract mContract = new Contract();
		
		cSet = mContract.getContractSetWithTSU(type, state, this.name);
		
		return cSet;
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
	
	public void completeQH(Integer cid,String content) {
		Process mProcess = new Process(cid, 1, 1, this.getName(), content);
		Contract mContract = new Contract(cid);
		mContract.setProcess(mProcess);
	}

		
}
