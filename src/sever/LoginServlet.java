package sever;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obj.User;

import org.json.JSONException;
import org.json.JSONObject;

import jdbc.UserJDBCAction;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String password = request.getParameter("pw");
		
		User m = UserJDBCAction.isMyUser(name, password);
		
		JSONObject jsonObject = new JSONObject(); 
		try {
			jsonObject.put("id", m.getId());
			jsonObject.put("access_taken", m.getAccess_taken());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//返回
	    response.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));  
	    response.setContentType("text/json; charset=UTF-8"); 
		
	    
	}

}
