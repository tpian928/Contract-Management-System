package obj;

import jdbc.Customer;

public class Hwriter {

	public static String writeTable(String str1,String str2,String str3) {

		StringBuilder builder = new StringBuilder();
		builder.append("<tr>");
		builder.append("<td><strong>"+str1+"</strong></td>");
		builder.append("<td>"+str2+"</td>");
		builder.append("<td>"+str3+"</td>");
		builder.append("</tr>");
		return builder.toString();
	}
	
	public static String wirteRoles(String roleid,String rolename) {
		StringBuilder builder = new StringBuilder();
		builder.append("<div class=\"ltitle\">");
		builder.append("<input id=\""+roleid+"\" type=\"checkbox\"/>"+rolename+"&nbsp&nbsp&nbsp&nbsp");
		builder.append("</div>");
		return builder.toString();
	}
	
	/**
	 * 写Div用于动态
	 * @param userid
	 * @param username
	 * @return
	 */
	public static String WriteDiv(int userid,String username) {
		StringBuilder builder = new StringBuilder();
		builder.append("<li class=\"item-content\">");
		builder.append("<div class=\"item-inner\">");
		builder.append("<div class=\"item-title\" id=\""+userid+"\">"+username+"</div>");
		builder.append("</div></li>");
		return builder.toString();
	}
	
	public static String WriteHQ(int userid,String username) {
		StringBuilder builder = new StringBuilder();
		builder.append("<li class=\"item-content\">");
		builder.append("<div class=\"item-inner\">");
		builder.append("<div class=\"item-title hq\" id=\""+userid+"\">"+username+"</div>");
		builder.append("</div></li>");
		return builder.toString();
	}
	public static String WriteSP(int userid,String username) {
		StringBuilder builder = new StringBuilder();
		builder.append("<li class=\"item-content\">");
		builder.append("<div class=\"item-inner\">");
		builder.append("<div class=\"item-title sp\" id=\""+userid+"\">"+username+"</div>");
		builder.append("</div></li>");
		return builder.toString();
	}
	public static String WriteQD(int userid,String username) {
		StringBuilder builder = new StringBuilder();
		builder.append("<li class=\"item-content\">");
		builder.append("<div class=\"item-inner\">");
		builder.append("<div class=\"item-title qd\" id=\""+userid+"\">"+username+"</div>");
		builder.append("</div></li>");
		return builder.toString();
	}
	
	/**
	 * 在look界面写一些东西
	 * @param title
	 * @param content
	 * @return
	 */
	public static String WirteMoreInLook(String title,String content) {
		StringBuilder builder = new StringBuilder();
		builder.append("<span>"+title+"</span>");
		builder.append("<textarea id=\"message2\" name=\"message2\">"+content+"</textarea>");
		return builder.toString();
	}
	
	public static String writeIndexJs() {
		StringBuilder builder = new StringBuilder();
		builder.append("var $$ = Dom7;");
		builder.append("$$('#title1').html('客户名字');");
		builder.append("$$('#title2').html('电话号码');");
		builder.append("$$('#title3').html('操作');");	  
		return builder.toString();
	}
	
	/**
	 * 返回在AddCustomer.html上的js
	 * @return
	 */
	public static String writeAddCJs(Customer mCustomer) {
		StringBuilder builder = new StringBuilder();
		builder.append("var $$ = Dom7;");
		builder.append("$$('#name').val('"+mCustomer.getName()+"');");
		builder.append("$$('#phone').val('"+mCustomer.getPhone()+"');");
		builder.append("$$('#address').val('"+mCustomer.getAddress()+"');");
		builder.append("$$('#fax').val('"+mCustomer.getFax()+"');");
		builder.append("$$('#email').val('"+mCustomer.getEmail()+"');");
		builder.append("$$('#bank').val('"+mCustomer.getBankName()+"');");
		builder.append("$$('#account').val('"+mCustomer.getBankAccount()+"');");
		builder.append("$$('#more').val('"+mCustomer.getMore()+"');");
		builder.append("$$('h1').html('查看客户')");
		
		return builder.toString();
	}

	
}
