package table;

public class Table {

	private int ID;
	private String name;
	private String password;
	private String hiddenName;
	private String firstOwner;
	
	public Table(int iD, String name, String password,String hiddenName,String firstOwner) {
		ID = iD;
		this.name = name;
		this.password = password;
		this.hiddenName = hiddenName;
		this.firstOwner = firstOwner;
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
	public String getFirstOwner() {
		return firstOwner;
	}
	public void setFirstOwner(String firstOwner) {
		this.firstOwner = firstOwner;
	}
	
}
