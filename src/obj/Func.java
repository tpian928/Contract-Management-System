package obj;

public class Func {

	public static boolean isEmpty(String str) {
		
		if (str.length()!=0&&str!=null&&str!="") {
			return false;
		}
		else{
			return true;
		}
	}
	
	public static void log(String str) {
		System.out.println(str);
	}
	
}
