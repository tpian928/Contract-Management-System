package obj;

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
	
	
	
}
