package sever;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import jdbc.Role;
import obj.User;


@WebServlet("/addRole")
public class AddRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddRoleServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//判断是否有这个权限
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String desc = request.getParameter("desc");
		String funcStr = request.getParameter("funcStr");
		String access_taken = request.getSession().getAttribute("access_taken").toString();
		String id = request.getSession().getAttribute("id").toString();
		User mUser = new User(id, access_taken);
		
		boolean exeres = false;
		
		if (mUser.hasFunc(16)||mUser.hasFunc(17)) {
			Role mRole = new Role();
			
			funcStr = funcStr.substring(1);
			ArrayList<Integer> intArr = new ArrayList<Integer>();
			String[] funcArr = funcStr.split("-");

			for(String i:funcArr){
				intArr.add(Integer.parseInt(i));
			}
			exeres = mRole.add(name, desc, intArr);
			
		}
		else {
			response.sendRedirect("noscope.html");
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

}
