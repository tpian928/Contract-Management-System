package sever;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.Role;
import obj.User;


@WebServlet("/addFunc")
public class AddFunc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddFunc() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断是否有这个权限
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String desc = request.getParameter("desc");
		String funcStr = request.getParameter("funcStr");
		String access_taken = request.getParameter("access_taken");
		String id = request.getParameter("id");
		User mUser = new User(id, access_taken);
		
		
		
	}

}
