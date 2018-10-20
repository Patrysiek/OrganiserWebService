package controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import services.UserService;
import user.User;

@Controller
@ComponentScan("/configFiles")
public class UserController {
	
	private UserService userService;
	private StringWriter writer;
	private ObjectMapper mapper;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	@Autowired
	public void setWriter(StringWriter writer) {
		this.writer = writer;
	}
	
	@RequestMapping(value="/users",method=RequestMethod.POST)
	@ResponseBody
	public String showUsers(Model model) throws JsonGenerationException, JsonMappingException, IOException{
		List<User> userList = userService.getAllUsers();
		mapper.writeValue(writer, userList);
		return writer.toString();
	}
	
	@RequestMapping(value="/getoneuser",method=RequestMethod.POST)
	@ResponseBody
	public String getOneUser(Model model, @RequestParam("login")String login) throws JsonGenerationException, JsonMappingException, IOException {
		User user = userService.getOneUser(login);
		mapper.writeValue(writer, user);
		return writer.toString();
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	@ResponseBody
	public boolean deleteUser(@RequestParam("login")String login) {
		return userService.deleteUser(login);
	}
	
	@RequestMapping(value="/createuser",method=RequestMethod.POST)
	@ResponseBody
	public boolean createUser(@RequestParam("login")String login,@RequestParam("name")String name,@RequestParam("password")String password) {
		return userService.createUser(login, name, password);
	}
	@RequestMapping(value="/createusertable",method=RequestMethod.POST)
	@ResponseBody
	public boolean createUserTable(@RequestParam("tablename")String tableName) {
		return userService.createUserTable(tableName);
	}
	@RequestMapping(value="/dropusertable",method=RequestMethod.POST)
	@ResponseBody
	public boolean dropUserTable(@RequestParam("tablename")String tableName) {
		return userService.dropUserTable(tableName);
	}
	

}
