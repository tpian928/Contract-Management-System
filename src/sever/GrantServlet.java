package sever;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.Role;
import obj.Func;
import obj.Hwriter;
import obj.User;

import org.apache.commons.io.FileUtils;


@WebServlet("/grant")
public class GrantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GrantServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if (request.getSession().getAttribute("access_taken")!=null) {
			
			String access_taken = request.getSession().getAttribute("access_taken").toString();
			String id = request.getSession().getAttribute("id").toString();
			
			ServletContext context = getServletContext();
			String fullPath = context.getRealPath("/grant.html");
			File htmlTemplateFile = new File(fullPath);
			String htmlString = FileUtils.readFileToString(htmlTemplateFile);
			
			String htmlContext="";
			User mUser = new User(id, access_taken);
			if (mUser.hasFunc(14)) {

				//展示有哪些角色
				Role mRole = new Role();
				for(Role tmp:mRole.getRoles()){
					htmlContext = htmlContext + Hwriter.wirteRoles(tmp.getRoleid(), tmp.getRolename());
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
