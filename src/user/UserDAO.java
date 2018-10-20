package user;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
		String sql = "INSERT INTO users(login,name,password) VALUES ?,?,?";
		return jdbcTemplate.update(sql, login,name,password) == 1;
	}
	
	public boolean deleteUser(String login) {
		String sql = "DELETE FROM users WHERE login = ?";
		return jdbcTemplate.update(sql, login) == 1;
	}

	public boolean createUserTable(String tableName) {
		String sql = "create table "+tableName+"(ID int auto_increment not null primary key,opis varchar(200),data date)";
		
		return jdbcTemplate.update(sql)==1;
	}

	public boolean dropUserTable(String tableName) {
		String sql = "drop table "+tableName;
		
		return jdbcTemplate.update(sql)==1;
	}
}
