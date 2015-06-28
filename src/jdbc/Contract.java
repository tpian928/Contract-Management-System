package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import obj.Global;


public class Contract {
	
	static Statement st;
	private static Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动

			con = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/cm?useUnicode=true&characterEncoding=UTF-8",
							"root", "wangyifei928");// 创建数据连接
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}
	
	
	private int cid;
	private String cname;
	private String customer;
	private String btime;
	private String etime;
	private String content;
	private String draftmanname;
	private String drafttime;
	@SuppressWarnings("unused")
	private int state;
	
	/**
	 * 初始构造方法
	 * @param cid
	 * @param cname
	 * @param customer
	 * @param btime
	 * @param etime
	 * @param content
	 * @param draftmanname
	 */
	public Contract(String cname, String customer, String btime,
			String etime, String content, String draftmanname) {
		//System.out.println(btime);
		this.cname = cname;
		this.customer = customer;
		this.btime = btime;
		this.etime = etime;
		this.content = content;
		this.draftmanname = draftmanname;
		int auto_id = 0;
		
		//进行合同插入操作，只有这里可以插入合同
		Connection conn = getConnection(); 
		try {
			String sql = "insert into contract (name,customer,content,beginTime,endTime,username) values('"+cname+"','"+customer+"','"+content+"','"+btime+"','"+etime+"','"+draftmanname+"')";
			
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				while (rs.next()) { 
				    auto_id = rs.getInt(1);
					System.out.println("auto_id is "+auto_id);
				}
				this.setCid(auto_id);
			} catch (SQLException e) {
				auto_id=-1;
				e.printStackTrace();
			}

			conn.close(); 
			
		} catch (SQLException e) {
			System.out.println("插入合同失败");
			System.err.println(e);
		}
		
	}
	
	/**
	 * 通过合同ID构造
	 * @param cid
	 */
	public Contract(int cid) {
		this.cid = cid;
		
		Connection conn = getConnection(); 
		try {
			String sql = "select *from contract where id = '"+cid+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				this.cname=rs.getString("name");
				this.customer=rs.getString("customer");
				this.content=rs.getString("content");
				Timestamp beginTime = rs.getTimestamp("beginTime");
				this.btime= new SimpleDateFormat("yyyy-MM-dd").format(beginTime);
				Timestamp endTime = rs.getTimestamp("endTime");
				this.etime= new SimpleDateFormat("yyyy-MM-dd").format(endTime);
				this.draftmanname=rs.getString("username");
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询role_id失败");
			System.err.println(e);
		}
		
	}
	
	public Contract() {
		
	}
	
	/**
	 * 设置状态
	 * @param type
	 * @return
	 */
	public boolean setState(int type) {
		
		boolean theresult = false;	
		
		if (hasThisContractInState(cid+"")==false) {
			Connection conn = getConnection(); 
			try {
				String sql = "insert into contract_state (cid,type) values('"+cid+"','"+type+"')";
				st = (Statement) conn.createStatement();
				int resultnum = st.executeUpdate(sql);
				System.out.println(resultnum);
				if(resultnum==1){
					theresult=true;
				}
				else{
					theresult=false;
				}
				conn.close(); 
			} catch (SQLException e) {
				System.out.println("插入合同进程失败");
				theresult=false;
				System.err.println(e);
			}			
		}
		else{
			Connection conn = getConnection(); 
			try {
				String sql = "update contract_state set type='"+type+"' where cid='"+this.getCid()+"'";
				st = (Statement) conn.createStatement();
				int resultnum = st.executeUpdate(sql);
				System.out.println(resultnum);
				if(resultnum==1){
					theresult=true;
				}
				else{
					theresult=false;
				}
				conn.close(); 
			} catch (SQLException e) {
				System.out.println("更新合同进程失败");
				theresult=false;
				System.err.println(e);
			}
		}
		
		return theresult;
	}

	public boolean updateContract() {
		
		boolean theresult = false;
		
		Connection conn = getConnection(); 
		try {
			String sql = "update contract set name='"+this.getCname()+"',customer='"+this.getCustomer()+"',content='"+this.getContent()+"',beginTime='"+this.getBtime()+"',endTime='"+this.getEtime()+"',username='"+this.getDraftmanname()+"' where id='"+this.getCid()+"'";
			st = (Statement) conn.createStatement();
			int resultnum = st.executeUpdate(sql);
			if(resultnum==1){
				theresult=true;
			}
			else{
				theresult=false;
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("更新合同失败");
			theresult=false;
			System.err.println(e);
		}			

		return theresult;
	}
	
	/**
	 * 设置进程，如果Process表中已经有username,cid,type则更新，否则插入
	 * @param p 进程对象
	 * @return
	 */
	public boolean insertProcess(Process p) {
		boolean theresult = false;
		Connection conn = getConnection(); 
		
		try {
			String sql = "insert into contract_process (cid,type,state,username,content) values('"+cid+"','"+p.getType()+"','"+p.getState()+"','"+p.getUsername()+"','"+p.getContent()+"')";
			//System.out.println(sql);
			st = (Statement) conn.createStatement();
			int resultnum = st.executeUpdate(sql);
			//System.out.println(resultnum);
			if(resultnum==1){
				theresult=true;
			}
			else{
				theresult=false;
			}
			
			conn.close(); 

		} catch (SQLException e) {
			System.out.println("插入合同进程失败");
			theresult=false;
			System.err.println(e);
		}
		
		return theresult;
	}
	
	public boolean updateProcess(Process p) {
		boolean theresult = false;
		Connection conn = getConnection(); 
		
		try {
			String sql = "update contract_process set state='"+p.getState()+"',content='"+p.getContent()+"' where cid='"+p.getCid()+"' AND type='"+p.getType()+"' AND username = '"+p.getUsername()+"'";
			System.out.println(sql);
			st = (Statement) conn.createStatement();
			int resultnum = st.executeUpdate(sql);
			if(resultnum==1){
				theresult=true;
			}
			else{
				theresult=false;
			}
			
			conn.close(); 

		} catch (SQLException e) {
			System.out.println("插入合同进程失败");
			theresult=false;
			System.err.println(e);
		}
		
		return theresult;
	}
	
	/**
	 * 判断某一种进程是否完成
	 * @param thecid 合同ID
	 * @param type 状态名称
	 * @return 已经完成返回1，如果小于1则未完成，平均值大于1表示有拒绝的
	 */
	private float havaCompleteType(int type) {
		float result = 0;
		Connection conn = getConnection(); 
		try {
			String sql = "select avg(state) from contract_process where cid='"+cid+"' and type='"+type+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) { 
				result = rs.getFloat("avg(state)");
			}
			System.out.println("avg is "+result);
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询制定状态的合同失败");
			System.err.println(e);
		}
		
		return result;
	}
	
	public boolean havaCompleteHQ() {
		float rNum = havaCompleteType(0);
		System.out.println("rnum is "+rNum);
		if (rNum==1) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean havaCompleteSP() {
		float rNum = havaCompleteType(1);
		System.out.println("rnum is "+rNum);
		if (rNum==1) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 判断是否完成了签订
	 * @return
	 */
	public boolean havaCompleteQD() {
		float rNum = havaCompleteType(2);
		System.out.println("rnum is "+rNum);
		if (rNum==1) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 判断在State表里面是否有这个记录
	 * @return
	 */
	public boolean hasThisContractInState(String cid) {
		boolean result = false;
		Connection conn = getConnection(); 
		try {
			String sql = "select cid from contract_state where cid = '"+cid+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) { 
				result = true;
			}
			conn.close(); 
		} catch (SQLException e) {
			result = false;
			System.out.println("查询制定状态的合同失败");
			System.err.println(e);
		}
		
		return result;

	}
	
	/**
	 * 得到制定状态的合同状态
	 * @param state
	 * @return
	 */
	public Set<Contract> getContractsByState(int state) {
		Set<Contract> contractSet = new HashSet<Contract>();
		
		//必须是要提及的user
		Connection conn = getConnection(); 
		try {
			String sql = "select * from contract where id in (select cid from contract_state where type='"+state+"')";
			
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) { 
				Contract mContract = new Contract(-1);
				mContract.cid=rs.getInt("id");
				mContract.cname=rs.getString("name");
				mContract.customer=rs.getString("customer");
				mContract.content=rs.getString("content");
				Timestamp beginTime = rs.getTimestamp("beginTime");
				mContract.btime= new SimpleDateFormat("yyyy-MM-dd").format(beginTime);
				Timestamp endTime = rs.getTimestamp("endTime");
				mContract.etime= new SimpleDateFormat("yyyy-MM-dd").format(endTime);
				Timestamp draftTime = rs.getTimestamp("drafttime");
				mContract.drafttime= new SimpleDateFormat("yyyy-MM-dd").format(draftTime);
				mContract.draftmanname=rs.getString("username");
				contractSet.add(mContract);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询制定状态的合同失败");
			Contract mContract = new Contract(-1);
			mContract.setContent(e+"");
			contractSet.add(mContract);
			System.err.println(e);
		}
		
		return contractSet;
	}
	
	/**
	 * 得到制定类型，制定状态的合同
	 * @param type 操作类型
	 * @param state 操作状态
	 * @return
	 */
	public Set<Process> getProcess(int type,int state) {
		Set<Process> processSet = new HashSet<Process>();
		
		Connection conn = getConnection(); 
		try {
			String sql = "select contract_process where type = '"+type+"' AND state='"+state+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				Process mProcess = new Process();
				mProcess.setCid(cid);
				mProcess.setContent(rs.getString("content"));
				mProcess.setState(state);
				Timestamp theTime = rs.getTimestamp("time");
				mProcess.setTime(new SimpleDateFormat("yyyy-MM-dd").format(theTime));
				mProcess.setType(type);
				mProcess.setUsername(rs.getString("username"));
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询制定状态的合同失败");
			System.err.println(e);
		}		
		
		return processSet;
	}
	
	/**
	 * 得到某个进度下的进度
	 * @param type
	 * @param state
	 * @param username
	 * @return
	 */
	public Set<Process> getProcess(int type,int state,String username) {
		Set<Process> processSet = new HashSet<Process>();
		
		Connection conn = getConnection(); 
		try {
			String sql = "select contract_process where type = '"+type+"' AND state='"+state+"' AND username='"+username+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				Process mProcess = new Process();
				mProcess.setCid(cid);
				mProcess.setContent(rs.getString("content"));
				mProcess.setState(state);
				Timestamp theTime = rs.getTimestamp("time");
				mProcess.setTime(new SimpleDateFormat("yyyy-MM-dd").format(theTime));
				mProcess.setType(type);
				mProcess.setUsername(rs.getString("username"));
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询制定状态的合同失败");
			System.err.println(e);
		}		
		
		return processSet;
	}
	
	
	/**
	 * 得到制定type,state,username下的合同
	 * @param type
	 * @param state
	 * @param username
	 * @return
	 */
	public Set<Contract> getContractSetWithTSU(int type,int state,String username,String q) {
		
		Set<Contract> cSet = new HashSet<Contract>();

		Connection conn = getConnection(); 
		try {
			String sql = "select *from contract where (id in (select cid from contract_process where type='"+type+"' AND state='"+state+"' AND username='"+username+"')) AND name like '%"+q+"%'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				Contract mContract = new Contract();
				mContract.setCname(rs.getString("name"));
				mContract.setCustomer(rs.getString("customer"));
				mContract.setContent(rs.getString("content"));
				Timestamp beginTime = rs.getTimestamp("beginTime");
				mContract.btime= new SimpleDateFormat("yyyy-MM-dd").format(beginTime);
				Timestamp endTime = rs.getTimestamp("endTime");
				mContract.etime= new SimpleDateFormat("yyyy-MM-dd").format(endTime);
				mContract.draftmanname=rs.getString("username");
				Timestamp dTime = rs.getTimestamp("drafttime");
				mContract.drafttime= new SimpleDateFormat("yyyy-MM-dd").format(dTime);	
				mContract.cid=rs.getInt("id");
				cSet.add(mContract);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("getContractSetWithTSU失败");
			System.err.println(e);
		}
		
		return cSet;
	}
	
	/**
	 * 为合同添加附件
	 * @param mAttachment 合同附件对象
	 * @return 成功则返回附件的ID，失败则返回－1
	 */
	public int addAttachment(Attachment mAttachment){

		int auto_id = -1;
		mAttachment.setCon_id(this.cid);
		
		//进行合同插入操作，只有这里可以插入合同
		Connection conn = getConnection(); 
		try {
			String sql = "insert into contract_attachment (fileName,path,type,con_id) values('"+mAttachment.getFilename()+"','"+mAttachment.getPath()+"','"+mAttachment.getType()+"','"+mAttachment.getCon_id()+"')";
			
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				while (rs.next()) { 
				    auto_id = rs.getInt(1);
					System.out.println("auto_id is "+auto_id);
				}
			} catch (SQLException e) {
				auto_id=-1;
				e.printStackTrace();
			}

			conn.close(); 

		} catch (SQLException e) {
			System.out.println("插入合同失败");
			System.err.println(e);
		}

		
		return auto_id;
	}
	
	public Set<Attachment> getAttachments() {
		Set<Attachment> attachmentSet = new HashSet<Attachment>();
		
		Connection conn = getConnection(); 
		try {
			String sql = "select *from contract_attachment where con_id='"+this.getCid()+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				Attachment mAttachment = new Attachment(rs.getString("fileName"), rs.getString("path"), rs.getInt("type"), this.getCid());
				attachmentSet.add(mAttachment);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("getAdvice失败");
			System.err.println(e);
		}
		
		return attachmentSet;
	}
	
	/**
	 * 得到会签意见
	 * @return String结果集
	 */
	public Set<String> getHQAdvice() {

		Set<String> strSet = new HashSet<String>();
		
		Connection conn = getConnection(); 
		try {
			String sql = "select *from contract_process where type='0' and cid='"+this.cid+"' and state=1";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				strSet.add(rs.getString("username")+":"+rs.getString("content"));
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("getAdvice失败");
			System.err.println(e);
		}
		
		return strSet;
		
	}
	
	/**
	 * 得到审批意见
	 * @return
	 */
	public Set<String> getSPAdvice() {
		
		Set<String> strSet = new HashSet<String>();
		
		Connection conn = getConnection(); 
		try {
			String sql = "select *from contract_process where type='1' and cid='"+this.cid+"' and state>0";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				String aStr = "";
				aStr=aStr+rs.getString("username")+":";
				String agree = "";
				if (rs.getInt("type")==1) {
					agree="同意";
				}
				else {
					agree="不同意";
				}
				aStr=aStr+agree+".  "+rs.getString("content");
				strSet.add(aStr);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("getSPAdvice失败");
			System.err.println(e);
		}
		
		return strSet;
		
	}
	
	//以下是get和set
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDraftmanname() {
		return draftmanname;
	}
	public void setDraftmanname(String draftmanname) {
		this.draftmanname = draftmanname;
	}

	public String getDrafttime() {
		return drafttime;
	}

	public void setDrafttime(String drafttime) {
		this.drafttime = drafttime;
	}

	public int getState() {
		
		int state=0;
		Connection conn = getConnection(); 
		try {
			String sql = "select type from contract_state where cid = '"+cid+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				if (rs.getInt("type")>state) {
					state=rs.getInt("type");
				}
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询制定状态的合同失败");
			System.err.println(e);
		}
		
		this.state=state;
		return state;
	}
	
}