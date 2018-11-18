package user;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("userDAO")
public class UserDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<User>getAllUser(){
		try {
		return jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
		}catch (Exception e) {
			System.out.println("Brak wynikow");
		}
		return null;
	}
	
	public User getOneUser(String login) {
		String sql = "SELECT * FROM users WHERE login = ?";
		try {
		return jdbcTemplate.queryForObject(sql,new  UserRowMapper(),login);
		}catch(Exception e) {
			System.out.println("Nie udane zapytanie");
		}
		return null;
	}
	
	public boolean createUser(String login,String name,String password) {
		try {
			String sql = "INSERT INTO users(login,name,password) VALUES( ?,?,?)";
			jdbcTemplate.update(sql, login,name,password);
			
		}catch(Exception ex) {
			return false;
		}
		return true;
	}
	
	public boolean deleteUser(String login) {
		String sql = "DELETE FROM users WHERE login = ?";
		return jdbcTemplate.update(sql, login) == 1;
	}

	public boolean createUserTable(String tableName) {
		try {
			String sql = "create table "+tableName+"(ID int auto_increment not null primary key,opis varchar(200),data date)";
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean createUserSharedTablesTable(String tableName) {
		try {
			String sql = "CREATE TABLE "+tableName+" (ID int(11) NOT NULL AUTO_INCREMENT,name varchar(45) NOT NULL,password varchar(45) NOT NULL,hiddenName varchar(45) NOT NULL,PRIMARY KEY (ID),UNIQUE KEY ID_UNIQUE (ID),UNIQUE KEY hiddenName_UNIQUE (hiddenName))";
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteFromUserSharedTablesTable(String tableName,String hiddenName) {
		try {
			String sql = "DELETE FROM "+tableName+"WHERE hiddenName = "+hiddenName;
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	public boolean insertIntoUserSharedTablesTable(String tableName,String hiddenName,String password) {
		try {
			String sql = "insert into "+tableName+"(name,password,hiddenName)VALUES(?,?,?)";
			jdbcTemplate.update(sql,tableName,hiddenName,password);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean dropUserTable(String tableName) {
		String sql = "drop table "+tableName;
		
		return jdbcTemplate.update(sql)==1;
	}

	public User login(String login, String password) {
		String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
		User user = null;
		try {
		user = (User) jdbcTemplate.queryForObject(sql, new UserRowMapper(),login,password);
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
		return user;
	}
}
