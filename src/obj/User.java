package obj;

import java.util.HashSet;
import java.util.Set;

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
	
}
