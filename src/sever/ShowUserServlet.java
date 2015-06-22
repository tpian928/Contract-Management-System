package sever;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.Role;


import org.apache.commons.io.FileUtils;

import obj.Func;
import obj.Hwriter;
import obj.User;


@WebServlet("/showuser")
public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShowUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getSession().getAttribute("access_taken")!=null) {
			request.setCharacterEncoding("UTF-8");
			String access_taken = request.getSession().getAttribute("access_taken").toString();
			String id = request.getSession().getAttribute("id").toString();
			
			String username = "";
			if (request.getParameter("name")!=null) {
				username = request.getParameter("name");
			}
			else{
				username="";
			}
			ServletContext context = getServletContext();
			String fullPath = context.getRealPath("/admin.html");
			File htmlTemplateFile = new File(fullPath);
			String htmlString = FileUtils.readFileToString(htmlTemplateFile);
			
			String result = "";
			
			User mUser = new User(id, access_taken);
			if (mUser.hasFunc(14)) {
				Set<User> userSet = Role.getUsers(username);
				userSet = Role.getUsers(username);
				
				for(User tmp:userSet){
					String str2 = "";
					for(String m:tmp.getRoleNameSet()){
						str2=str2+" "+m;
					}
					result=result+ Hwriter.writeTable(tmp.getName(), str2, "<a id=\""+tmp.getId()+"\">授权</a>");
				}
				
			}
			else{
				Func.log("没有权限");
			}
			
			htmlString = htmlString.replace("$rs",result);		
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
