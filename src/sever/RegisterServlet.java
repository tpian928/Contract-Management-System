package sever;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

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
		String password = request.getParameter("password");
		String idString = UserJDBCAction.isMyUser(name, password);
		
		//result
		//0-成功注册
		JSONObject jsonObject = new JSONObject(); 
		
		if (idString.length()==0) {
			try {
				UserJDBCAction.addUser(name, password);
				idString = UserJDBCAction.isMyUser(name, password);
				jsonObject.put("id", idString);
			} catch (Exception e) {
				try {
					jsonObject.put("id", "-1");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}			
			}
		}
		else{
			try {
				jsonObject.put("id", "-2");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		//返回
	    response.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));  
	    response.setContentType("text/json; charset=UTF-8"); 
		
	}

}
