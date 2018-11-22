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

	
}
