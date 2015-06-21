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
	
	
	/**
	 * 设置进程
	 * @param p 进程对象
	 * @return
	 */
	public boolean setProcess(Process p) {
		boolean theresult = false;
		Connection conn = getConnection(); 
		try {
			String sql = "insert into contract_process (cid,type,state,username,content,username) values('"+cid+"','"+p.getType()+"','"+p.getState()+"','"+p.getUsername()+"','"+p.getContent()+"','"+p.getUsername()+"')";
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
		return theresult;
	}
	
	/**
	 * 设置状态
	 * @param type
	 * @return
	 */
	public boolean setState(int type) {
		
		boolean theresult = false;		
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
		return theresult;
		
	}
	
	public Set<Contract> getContractsByState(int state) {
		Set<Contract> contractSet = new HashSet<Contract>();
		
		//必须是要提及的user
		Connection conn = getConnection(); 
		try {
			String sql = "select contract_state where type = '"+state+"'";
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
				mContract.draftmanname=rs.getString("username");
				contractSet.add(mContract);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询制定状态的合同失败");
			System.err.println(e);
		}
		
		return contractSet;
	}
	
	/**
	 * 
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
	
	
}
