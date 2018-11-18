package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasks.Task;
import tasks.TaskDAO;

@Service("taskService")
public class TaskService {

	
	TaskDAO taskDAO;
	
	@Autowired
	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}
	
	public List<Task> getAllTasks(String tableName){
		return taskDAO.getAllTasks(tableName);
	}
	
	public List<Task> getAllTaskFromDay(String tableName,String date){
		
		return taskDAO.getAllTasksFromDay(tableName, date);
	}
	
	public Task getTask(String tableName,int ID) {
		return taskDAO.getTask(tableName, ID);
	}
	
	public void createTaskTable(String tableName) {
		taskDAO.createTaskTable(tableName);
	}
	public void insertTask(String tableName,String date,String description,String status) {
		taskDAO.insertTask(tableName, date, description,status);
	}
	public void deleteTask(String tableName, String IDs) {
		taskDAO.deleteTask(tableName, IDs);
	}
	public void dropTable(String tableName) {
		taskDAO.dropTable(tableName);
	}

	public void updateTask(String tableName, String ID, String status) {
			taskDAO.updateTask(tableName,ID,status);
	}
	
}
