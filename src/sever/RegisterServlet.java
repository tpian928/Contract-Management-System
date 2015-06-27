package sever;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obj.Func;
import obj.User;

import org.json.JSONException;
import org.json.JSONObject;

import jdbc.UserJDBCAction;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String password = request.getParameter("pw");
		//TODO判断是否有这个用户名
		
		User m = UserJDBCAction.addUser(name, password);
		
		JSONObject jsonObject = new JSONObject();
		if (Func.isEmpty(m.getId())==false) {
			request.getSession().setAttribute("access_taken", m.getAccess_taken());
			request.getSession().setAttribute("id", m.getId());
			try {
				jsonObject.put("result", true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				jsonObject.put("result", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}			
		}
		//返回
	    response.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));  
	    response.setContentType("text/json; charset=UTF-8"); 
		
	}
	
	

}
