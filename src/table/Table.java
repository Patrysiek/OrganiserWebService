package table;

public class Table {

	private int ID;
	private String name;
	private String password;
	private String hiddenName;
	
	
	
	public Table(int iD, String name, String password,String hiddenName) {
		ID = iD;
		this.name = name;
		this.password = password;
		this.hiddenName = hiddenName;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHiddenName() {
		return hiddenName;
	}
	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}
	
	
}
