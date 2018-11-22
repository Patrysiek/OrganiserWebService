package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.User;
import user.UserDAO;

@Service("userService")
public class UserService {

	
	private UserDAO userDAO;
	
	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public List<User>getAllUsers() {
		return userDAO.getAllUser();
	}
	
	public User getOneUser(String login) {
		return userDAO.getOneUser(login);
	}
	public boolean deleteUser(String login) {
		
		return userDAO.deleteUser(login);
	}

	public boolean createUser(String login,String name, String password) {
		return userDAO.createUser(login, name, password);
	}

	public User login(String login, String password) {
		return userDAO.login(login,password);
	}

	
}
