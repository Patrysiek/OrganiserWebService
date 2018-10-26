package tasks;

import java.sql.Date;

public class Task {

	private int ID;
	private Date date;
	private String description;
	
	public Task(int ID, Date date, String description) {
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
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
