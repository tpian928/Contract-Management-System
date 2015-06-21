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
			
			String htmlContext="";
			User mUser = new User(id, access_taken);
			if (mUser.hasFunc(8)) {
				
				int page = 0;
				
				if (request.getParameter("page")!=null) {
					page = Integer.parseInt(request.getParameter("page"));
				}
				
				Set<User> userSet = new HashSet<User>();
				
				switch (page) {
				case 0:
					System.out.println("page 0 ");
					userSet = mUser.getAllUserWithFunc(5);
					for(User tmp:userSet){
						htmlContext=htmlContext+Hwriter.WriteDiv(Integer.parseInt(tmp.getId()), tmp.getName());
					}
					break;
				case 1:
					System.out.println("page 1 ");
					userSet = mUser.getAllUserWithFunc(6);
					for(User tmp:userSet){
						htmlContext=htmlContext+Hwriter.WriteDiv(Integer.parseInt(tmp.getId()), tmp.getName());
					}
					break;
				case 2:
					System.out.println("page 2 ");
					userSet = mUser.getAllUserWithFunc(7);
					for(User tmp:userSet){
						htmlContext=htmlContext+Hwriter.WriteDiv(Integer.parseInt(tmp.getId()), tmp.getName());
					}
					break;
				default:
					break;
				}
				
			}
			else{
				Func.log("没有权限");
			}
			
			htmlString = htmlString.replace("$rs",htmlContext);		
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
