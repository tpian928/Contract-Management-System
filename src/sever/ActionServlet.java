package sever;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obj.Admin;
import obj.User;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import jdbc.Contract;
import jdbc.UserJDBCAction;


@WebServlet("/cAction")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload uploader = null;   

    public ActionServlet() {
        super();
        
    }

	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post in ActionServlet");
		boolean exeres = false;
		if (request.getSession().getAttribute("access_taken")!=null) {
			System.out.println("if");
			String access_taken = request.getSession().getAttribute("access_taken").toString();
			String id = request.getSession().getAttribute("id").toString();
			String action = request.getParameter("action");
			User mUser = new User(id, access_taken);
			
			if (action.equals("draft")) {//起草合同
				if (mUser.hasFunc(1)) {
					System.out.println("draft");
										
					String cname = request.getParameter("cname");
					String customer = request.getParameter("customer");
					String message = request.getParameter("message");
					String bdate = request.getParameter("bdate");
					System.out.println("bdate is "+bdate);
					String edate = request.getParameter("edate");
					@SuppressWarnings("unused")
					Contract mContract = new Contract(cname, customer, bdate, edate, message, UserJDBCAction.getUserById(id).getName());
					exeres=true;
					
					//路径加入数据库
					
					
				}
				else {
					response.sendRedirect("noscope.html");
					exeres=false;
				}
			}
			else if (action.equals("csaction")) {//会签
				if (mUser.hasFunc(5)) {
					System.out.println("draft");
					
					//加入Contact_process
					mUser.completeQH(Integer.parseInt(request.getSession().getAttribute("cid").toString()),request.getParameter("hqs"));
					
					exeres=true;
				}
				else {
					response.sendRedirect("noscope.html");
					exeres=false;
				}
			}
			
			else if (action.equals("ac")) {//分配
				System.out.println("ac");
				
				String hqUserStr = request.getParameter("hqUserStr");
				String spUserStr = request.getParameter("spUserStr");
				String qdUserStr = request.getParameter("qdUserStr");
				int cid = Integer.parseInt(request.getSession().getAttribute("cid").toString());
				
				hqUserStr = hqUserStr.substring(1);
				String[] hqArr = hqUserStr.split("-");
				
				for(String tmp:hqArr){
					
					Admin admin = new Admin(id, access_taken);
					String username = UserJDBCAction.getUserById(tmp).getName();
					admin.arrange(cid, 0, username);
				}
				
				spUserStr = spUserStr.substring(1);
				String[] spArr = spUserStr.split("-");
				for(String tmp:spArr){
					Admin admin = new Admin(id, access_taken);
					String username = UserJDBCAction.getUserById(tmp).getName();
					admin.arrange(cid, 1, username);
				}
				
				qdUserStr = qdUserStr.substring(1);
				String[] qdArr = qdUserStr.split("-");
				for(String tmp:qdArr){
					Admin admin = new Admin(id, access_taken);
					String username = UserJDBCAction.getUserById(tmp).getName();
					admin.arrange(cid, 2, username);
				}
				
				Contract contract = new Contract(cid);
				
				contract.setState(1);
				
				exeres=true;
				
			}
			else if (action.equals("dg")) {//定稿确认
				
				if (mUser.hasFunc(2)) {
					String message = request.getParameter("message");
					int cid = Integer.parseInt(request.getSession().getAttribute("cid").toString());
					Contract mContract = new Contract(cid);
					mContract.setContent(message);
					exeres = mContract.updateContract();
					if (exeres) {
						mContract.setState(3);
					}						
				}
				else {
					response.sendRedirect("noscope.html");
				}

			}
						
			else if (action.equals("spaction")) {
				System.out.println("用户要审批");
				
				if (mUser.hasFunc(9)) {
					
					exeres = mUser.completeSP(Integer.parseInt(request.getSession().getAttribute("cid").toString()), request.getParameter("sps"), Boolean.valueOf(request.getParameter("agree")));

				}
				else {
					exeres=false;
					response.sendRedirect("noscope.html");
				}
				
			}
			
			else if (action.equals("qdaction")) {
				System.out.println("用户要签订");
				
				if (mUser.hasFunc(7)) {
					
					exeres = mUser.completeQD(Integer.parseInt(request.getSession().getAttribute("cid").toString()), request.getParameter("message"));
					
				}
				else {
					exeres=false;
					response.sendRedirect("noscope.html");
				}
				
			}
			
			JSONObject object = new JSONObject();
			try {
				object.put("result", exeres);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		    response.getOutputStream().write(object.toString().getBytes("UTF-8"));  
		    response.setContentType("text/json; charset=UTF-8"); 
			
		}
		else{
			response.sendRedirect("login.html");		
		}

		
	}

}
