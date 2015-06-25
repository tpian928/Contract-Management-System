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
import obj.Hwriter;
import obj.User;

import org.apache.commons.io.FileUtils;


@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public IndexServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getSession().getAttribute("access_taken")!=null) {
			request.setCharacterEncoding("UTF-8");
			String access_taken = request.getSession().getAttribute("access_taken").toString();
			String id = request.getSession().getAttribute("id").toString();
			User mUser = new User(id, access_taken);
			
			int page = 0;
			if (request.getParameter("page")!=null) {
				page = Integer.parseInt(request.getParameter("page"));
			}

			ServletContext context = getServletContext();
			String fullPath = context.getRealPath("/index.html");
			File htmlTemplateFile = new File(fullPath);
			String htmlString = FileUtils.readFileToString(htmlTemplateFile);
			
			String htmlContent = "";
			
			switch (page) {
			case 0://待会签合同
				
				Set<Contract> contractSet = mUser.getContracts(0, 0);
				
				htmlString=htmlString.replace("$title", "待会签合同");
				htmlString=htmlString.replace("$ht", "待会签合同");
				for(Contract tmp:contractSet){
					if (tmp.getState()==1) {
						htmlContent=htmlContent+Hwriter.writeTable(tmp.getCname(), tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\" href=\"/CM/cAShow?cid="+tmp.getCid()+"&cname="+tmp.getCname()+"&action=cs\">会签</a>");
					}
				}
				break;
			case 1:
				Set<Contract> contractSet2 = mUser.getContracts(0, 1);
				htmlString=htmlString.replace("$title", "已会签合同");
				htmlString=htmlString.replace("$ht", "已会签合同");
				for(Contract tmp:contractSet2){
					htmlContent=htmlContent+Hwriter.writeTable(tmp.getCname(), tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\" href=\"/CM/cAShow?cid="+tmp.getCid()+"&action=look\">查看</a>");
				}
				break;
			case 2:
				Set<Contract> contractSet3 = mUser.getContracts(1, 0);
				htmlString=htmlString.replace("$title", "待审批合同");
				htmlString=htmlString.replace("$ht", "待审批合同");
				for(Contract tmp:contractSet3){
					if (tmp.getState()==1) {
						htmlContent=htmlContent+Hwriter.writeTable(tmp.getCname(), tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\" href=\"/CM/cAShow?cid="+tmp.getCid()+"&cname="+tmp.getCname()+"&action=sp\">审批</a>");
					}
				}				
				break;
			case 3:
				Set<Contract> contractSet4 = mUser.getContracts(1, 1);
				htmlString=htmlString.replace("$title", "已审批合同");
				htmlString=htmlString.replace("$ht", "已审批合同");
				for(Contract tmp:contractSet4){
					htmlContent=htmlContent+Hwriter.writeTable(tmp.getCname(), tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\" href=\"/CM/cAShow?cid="+tmp.getCid()+"&action=look\">查看</a>");
				}				
				break;
			case 4:
				Set<Contract> contractSet5 = mUser.getContracts(2, 0);
				htmlString=htmlString.replace("$title", "待签订合同");
				htmlString=htmlString.replace("$ht", "待签订合同");
				for(Contract tmp:contractSet5){
					if (tmp.getState()==3) {
						htmlContent=htmlContent+Hwriter.writeTable(tmp.getCname(), tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\" href=\"/CM/cAShow?cid="+tmp.getCid()+"&cname="+tmp.getCname()+"&action=qd\">签订</a>");
					}
				}				
				break;
			case 5:
				Set<Contract> contractSet6 = mUser.getContracts(2, 1);
				htmlString=htmlString.replace("$title", "已签订合同");
				htmlString=htmlString.replace("$ht", "已签订合同");
				for(Contract tmp:contractSet6){
					htmlContent=htmlContent+Hwriter.writeTable(tmp.getCname(), tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\" href=\"/CM/cAShow?cid="+tmp.getCid()+"&action=look\">查看</a>");
				}				
				break;
				
			case 6:
				
				Set<Contract> contractSet7 = mUser.getDingGao();
				htmlString=htmlString.replace("$title", "待定稿合同");
				htmlString=htmlString.replace("$ht", "待定稿合同");
				for(Contract tmp:contractSet7){
					htmlContent=htmlContent+Hwriter.writeTable(tmp.getCname(), tmp.getDrafttime(), "<a id=\""+tmp.getCid()+"\" href=\"/CM/cAShow?cid="+tmp.getCid()+"&action=dgshow&cname="+tmp.getCname()+"\">定稿</a>");
				}	
				break;
			default:
				
				break;
			}
			
			htmlString = htmlString.replace("$rs",htmlContent);		
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
