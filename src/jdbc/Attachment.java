package jdbc;

public class Attachment {

	public Attachment(String filename, String path, int type, int con_id) {
		super();
		this.filename = filename;
		this.path = path;
		this.type = type;
		this.con_id = con_id;
	}
	
	private String filename,path;
	private int type,con_id;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCon_id() {
		return con_id;
	}
	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}
	
}
