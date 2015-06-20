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
	
}
