package sever;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String password = request.getParameter("password");
		String idString = UserJDBCAction.isMyUser(name, password);
		JSONObject jsonObject = new JSONObject(); 
		try {
			if (idString.length()!=0) {
				//返回idString
				jsonObject.put("id", idString);
			}
			else {
				jsonObject.put("id", "");
			}
		} catch (Exception e) {
			try {
				jsonObject.put("id", "");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		//返回
	    response.getOutputStream().write(jsonObject.toString().getBytes("UTF-8"));  
	    response.setContentType("text/json; charset=UTF-8"); 
		
	}

}
