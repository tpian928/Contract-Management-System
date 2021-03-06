package sever;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.Attachment;
import jdbc.Contract;
import jdbc.Customer;

import org.apache.commons.io.FileUtils;

import obj.Func;
import obj.Hwriter;
import obj.User;

@WebServlet("/cAShow")
public class ActionShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String cid = "";

    public ActionShowServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("cAShow");
		request.setCharacterEncoding("UTF-8");
		if (request.getSession().getAttribute("access_taken")!=null&&request.getParameter("cid")!=null) {
			
			String access_taken = request.getSession().getAttribute("access_taken").toString();
			String id = request.getSession().getAttribute("id").toString();
			User mUser = new User(id, access_taken);
			
			String cname = request.getParameter("cname");
			cid = request.getParameter("cid");
			request.getSession().setAttribute("cid", cid);
			
			ServletContext context = getServletContext();
			String htmlString = "";
			
			
			if (request.getParameter("action").equals("ac")) {
				
				String fullPath = context.getRealPath("/ac.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);
				
				//设置合同名字
				htmlString = htmlString.replace("$cname",cname);
				
				String htmlContext1="";
				String htmlContext2="";
				String htmlContext3="";
				
				
				if (mUser.hasFunc(8)) {
					
					Set<User> userSet = new HashSet<User>();
					
					userSet = mUser.getAllUserWithFunc(5);
					for(User tmp:userSet){
						htmlContext1=htmlContext1+Hwriter.WriteHQ(Integer.parseInt(tmp.getId()), tmp.getName());
					}
					
					userSet = mUser.getAllUserWithFunc(6);
					for(User tmp:userSet){
						htmlContext2=htmlContext2+Hwriter.WriteSP(Integer.parseInt(tmp.getId()), tmp.getName());
					}

					userSet = mUser.getAllUserWithFunc(7);
					for(User tmp:userSet){
						htmlContext3=htmlContext3+Hwriter.WriteQD(Integer.parseInt(tmp.getId()), tmp.getName());
					}

					
				}
				else{
					response.sendRedirect("noscope.html");
					Func.log("没有权限");
				}
				
				htmlString = htmlString.replace("$rs1",htmlContext1);		
				htmlString = htmlString.replace("$rs2",htmlContext2);		
				htmlString = htmlString.replace("$rs3",htmlContext3);				
			}
			else if (request.getParameter("action").equals("cs")) {//会签合同
				
				String fullPath = context.getRealPath("/cs.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);
				
				//设置合同名字
				htmlString = htmlString.replace("$cname",cname);
								
				if (mUser.hasFunc(5)) {
										
				}
				else{
					response.sendRedirect("noscope.html");
					Func.log("没有权限");
				}
				
				htmlString = htmlString.replace("$cname",request.getParameter("cname"));
				
			}
			else if (request.getParameter("action").equals("dgshow")) {
				String fullPath = context.getRealPath("/dg.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);
				
				Contract mContract = new Contract(Integer.parseInt(request.getParameter("cid")));
				
				//设置合同名字
				htmlString = htmlString.replace("$javaname",mContract.getCname());
				htmlString = htmlString.replace("$javacustomer",mContract.getCustomer());
				htmlString = htmlString.replace("$javabtime",mContract.getBtime());
				htmlString = htmlString.replace("$javaetime",mContract.getEtime());
				htmlString = htmlString.replace("$content",mContract.getContent());
				
			}
			else if (request.getParameter("action").equals("spshow")) {
				System.out.println("显示要审批的合同");
				
				String fullPath = context.getRealPath("/sp.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);
								
				//设置合同名字
				htmlString = htmlString.replace("$cname",request.getParameter("cname"));
				
			}
			else if (request.getParameter("action").equals("qdshow")) {
				String fullPath = context.getRealPath("/qd.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);
				
				Contract mContract = new Contract(Integer.parseInt(request.getParameter("cid")));
				
				//设置合同名字
				htmlString = htmlString.replace("$javaname",mContract.getCname());
				htmlString = htmlString.replace("$javacustomer",mContract.getCustomer());
				
			}
			else if (request.getParameter("action").equals("look")) {
				String fullPath = context.getRealPath("/look.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);
				
				Contract mContract = new Contract(Integer.parseInt(request.getParameter("cid")));
				
				//设置合同名字
				htmlString = htmlString.replace("$javaname",mContract.getCname());
				htmlString = htmlString.replace("$javacustomer",mContract.getCustomer());
				htmlString = htmlString.replace("$javabtime",mContract.getBtime());
				htmlString = htmlString.replace("$javaetime",mContract.getEtime());
				htmlString = htmlString.replace("$content",mContract.getContent());
				
				htmlString = htmlString.replace("$more","");
				
				Attachment attachment = new Attachment();
				for (Attachment tmp:mContract.getAttachments()) {
					attachment=tmp;
				}
				if (mContract.getAttachments().size()>0) {
					htmlString=htmlString.replace("$js","window.location.href=\"/CM/uploadFiles?fileName="+attachment.getFilename()+"\"");
					
				}
				else {
					htmlString=htmlString.replace("$js","");
				}
				
			}
			
			else if (request.getParameter("action").equals("lookhq")) {
				String fullPath = context.getRealPath("/look.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);
				
				Contract mContract = new Contract(Integer.parseInt(request.getParameter("cid")));
				
				//设置合同名字
				htmlString = htmlString.replace("$javaname",mContract.getCname());
				htmlString = htmlString.replace("$javacustomer",mContract.getCustomer());
				htmlString = htmlString.replace("$javabtime",mContract.getBtime());
				htmlString = htmlString.replace("$javaetime",mContract.getEtime());
				htmlString = htmlString.replace("$content",mContract.getContent());
				Set<String> adviceSet = new HashSet<String>();
				adviceSet=mContract.getHQAdvice();
				String adviceStr = "";
				for(String tmp:adviceSet){
					adviceStr=adviceStr+tmp+"\n";
				}
				htmlString = htmlString.replace("$more",Hwriter.WirteMoreInLook("会签意见", adviceStr));
				
				Attachment attachment = new Attachment();
				for (Attachment tmp:mContract.getAttachments()) {
					attachment=tmp;
				}
				if (mContract.getAttachments().size()>0) {
					htmlString=htmlString.replace("$js","window.location.href=\"/CM/uploadFiles?fileName="+attachment.getFilename()+"\"");
					
				}
				else {
					htmlString=htmlString.replace("$js","");
				}
				
			}
			else if (request.getParameter("action").equals("lookc")) {
				String fullPath = context.getRealPath("/addCustomer.html");
				File htmlTemplateFile = new File(fullPath);
				htmlString = FileUtils.readFileToString(htmlTemplateFile);				
				
				Customer mCustomer = new Customer(Integer.parseInt(request.getParameter("cid")));
				System.out.println("result is "+Hwriter.writeAddCJs(mCustomer));
				htmlString = htmlString.replace("addcjs",Hwriter.writeAddCJs(mCustomer));
				
			}

			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
		    PrintWriter out = response.getWriter();
		    out.println(htmlString);

		}
		else{
			response.sendRedirect("login.html");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
