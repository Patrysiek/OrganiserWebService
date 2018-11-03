package tasks;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("TaskDAO")
public class TaskDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Task> getAllTasks(String tableName) {
		try {
			return jdbcTemplate.query("SELECT * FROM " + tableName, new TaskRowMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}
	
	public List<Task> getAllTasksFromDay(String tableName,String sDate) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate  date = LocalDate.parse(sDate,formatter);
			return jdbcTemplate.query("SELECT * FROM " + tableName+" WHERE date = ?", new TaskRowMapper(),date);
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
	
	public void deleteTask(String tableName,String IDs) {
		try {
			jdbcTemplate.update("DELETE FROM " + tableName+" WHERE ID IN("+IDs+")");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void insertTask(String tableName,String sDate,String description) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate  date = LocalDate.parse(sDate,formatter);
			jdbcTemplate.update("INSERT INTO " + tableName + "(date,description)VALUES(?,?)",date,description);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
