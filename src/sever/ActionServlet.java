package sever;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obj.User;

import org.json.JSONException;
import org.json.JSONObject;

import jdbc.Contract;
import jdbc.UserJDBCAction;


@WebServlet("/cAction")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ActionServlet() {
        super();
        
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
			if (action.equals("draft")) {//起草合同
				User mUser = new User(id, access_taken);
				if (mUser.hasFunc(1)) {
					System.out.println("draft");
					String cname = request.getParameter("cname");
					String customer = request.getParameter("customer");
					String message = request.getParameter("message");
					String bdate = request.getParameter("bdate");
					System.out.println("bdate is "+bdate);
					String edate = request.getParameter("edate");
					Contract mContract = new Contract(cname, customer, bdate, edate, message, UserJDBCAction.getUserById(id).getName());
					exeres=true;
				}
				else {
					exeres=false;
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
