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
		
		return jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
	}
	
	public User getOneUser(String login) {
		String sql = "SELECT * FROM user WHERE login = ?";
		return jdbcTemplate.queryForObject(sql,new  UserRowMapper(),login);
	}
	
	public boolean createUser(String login,String name,String password) {
		String sql = "INSERT INTO users(login,name,password) VALUES ?,?,?";
		return jdbcTemplate.update(sql, login,name,password) == 1;
	}
	
	public boolean deleteUser(String login) {
		String sql = "DELETE FROM users WHERE login = ?";
		return jdbcTemplate.update(sql, login) == 1;
	}
}
