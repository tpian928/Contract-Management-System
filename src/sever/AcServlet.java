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

import org.apache.commons.io.FileUtils;

import obj.Func;
import obj.Hwriter;
import obj.User;
import jdbc.Role;
import jdbc.UserJDBCAction;


@WebServlet("/ac")
public class AcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AcServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		if (request.getSession().getAttribute("access_taken")!=null) {
			
			String access_taken = request.getSession().getAttribute("access_taken").toString();
			String id = request.getSession().getAttribute("id").toString();
			
			ServletContext context = getServletContext();
			String fullPath = context.getRealPath("/ac.html");
			File htmlTemplateFile = new File(fullPath);
			String htmlString = FileUtils.readFileToString(htmlTemplateFile);
			
			String htmlContext1="";
			String htmlContext2="";
			String htmlContext3="";
			
			User mUser = new User(id, access_taken);
			if (mUser.hasFunc(8)) {
								
				Set<User> userSet = new HashSet<User>();
				
				userSet = mUser.getAllUserWithFunc(5);
				for(User tmp:userSet){
					htmlContext1=htmlContext1+Hwriter.WriteDiv(Integer.parseInt(tmp.getId()), tmp.getName());
				}
				
				userSet = mUser.getAllUserWithFunc(6);
				for(User tmp:userSet){
					htmlContext2=htmlContext2+Hwriter.WriteDiv(Integer.parseInt(tmp.getId()), tmp.getName());
				}

				userSet = mUser.getAllUserWithFunc(7);
				for(User tmp:userSet){
					htmlContext3=htmlContext3+Hwriter.WriteDiv(Integer.parseInt(tmp.getId()), tmp.getName());
				}

				
			}
			else{
				Func.log("没有权限");
			}
			
			htmlString = htmlString.replace("$rs1",htmlContext1);		
			htmlString = htmlString.replace("$rs2",htmlContext2);		
			htmlString = htmlString.replace("$rs3",htmlContext3);		
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
