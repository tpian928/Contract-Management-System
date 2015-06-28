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

import jdbc.Contract;
import obj.Func;
import obj.Hwriter;
import obj.User;

import org.apache.commons.io.FileUtils;

@WebServlet("/needac")
public class NeedAcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NeedAcServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if (request.getSession().getAttribute("access_taken")!=null) {
			
			String access_taken = request.getSession().getAttribute("access_taken").toString();
			String id = request.getSession().getAttribute("id").toString();
			
			ServletContext context = getServletContext();
			String fullPath = context.getRealPath("/needac.html");
			File htmlTemplateFile = new File(fullPath);
			String htmlString = FileUtils.readFileToString(htmlTemplateFile);
			
			String result = "";
			
			User mUser = new User(id, access_taken);
			if (mUser.hasFunc(3)) {
				Contract mContract = new Contract();
				Set<Contract> contractSet = mContract.getContractsByState(0);
				
				result=result+contractSet.size();
				
				for(Contract tmp:contractSet){
					result=result+Hwriter.writeTable(contractSet.size()+"", tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\">分配</a>");
				}
				
			}
			else{
				response.sendRedirect("noscope.html");
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
