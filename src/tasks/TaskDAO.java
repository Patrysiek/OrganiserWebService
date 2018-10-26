package tasks;


import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("TaskDAO")
public class TaskDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplate setJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	public List<Task> getAllTasks(String tableName) {
		try {
			return jdbcTemplate.query("SELECT * FROM " + tableName, new TaskRowMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}
	
	public List<Task> getAllTasksFromDay(String tableName,Date date) {
		try {
			return jdbcTemplate.query("SELECT * FROM " + tableName+" WHERE date ="+date.toString(), new TaskRowMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public void createTaskTable(String tableName) {
		
		
		jdbcTemplate.update("CREATE TABLE IF NOT EXISTS "+tableName+"(ID int primary key NOT NULL  auto_increment,Date date,description varchar(40))");
	}
	
	public void dropTable(String tableName) {
		jdbcTemplate.update("DROP TABLE "+tableName);
	}
	
	public Task getTask(String tableName,int ID) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM " + tableName+" WHERE ID ="+ID, new TaskRowMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public Task deleteTask(String tableName,int ID) {
		try {
			return jdbcTemplate.queryForObject("DELETE FROM " + tableName+" WHERE ID ="+ID, new TaskRowMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public void insertTask(String tableName,Date date,String description) {
		try {
			jdbcTemplate.update("INSERT INTO " + tableName + "(date,descriptiom)VALUES(?,?)",date,description);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
