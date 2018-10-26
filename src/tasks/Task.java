package tasks;

public class Task {

	private int ID;
	private String date;
	private String description;
	
	public Task(int ID, String date, String description) {
		super();
		this.ID = ID;
		this.date = date;
		this.description = description;
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Task [ID=" + ID + ", date=" + date + ", description=" + description + "]";
	}
	
	
	
	
	
	
}
