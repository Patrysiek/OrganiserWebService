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
			e.printStackTrace();
			return null;
		}
		
	}
	
	public User getOneUser(String login) {
		String sql = "SELECT * FROM users WHERE login = ?";
		try {
		return jdbcTemplate.queryForObject(sql,new  UserRowMapper(),login);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public User login(String login, String password) {
		String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
		User user = null;
		try {
		user = (User) jdbcTemplate.queryForObject(sql, new UserRowMapper(),login,password);
		return user;
		}catch(EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	public boolean createUser(String login,String name,String password) {
		try {
			String sql = "INSERT INTO users(login,name,password) VALUES( ?,?,?)";
			jdbcTemplate.update(sql, login,name,password);
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteUser(String login) {
		String sql = "DELETE FROM users WHERE login = ?";
		return jdbcTemplate.update(sql, login) == 1;
	}

	
}
