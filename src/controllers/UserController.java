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

import configFiles.ParserConfig;
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

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	public String showUsers(Model model) throws JsonGenerationException, JsonMappingException, IOException {
		List<User> userList = userService.getAllUsers();
		writer = new ParserConfig().getWriter();
		mapper.writeValue(writer, userList);
		return writer.toString();
	}

	@RequestMapping(value = "/getoneuser", method = RequestMethod.POST)
	@ResponseBody
	public String getOneUser(Model model, @RequestParam("login") String login)
			throws JsonGenerationException, JsonMappingException, IOException {
		User user = userService.getOneUser(login);
		writer = new ParserConfig().getWriter();
		mapper.writeValue(writer, user);
		return writer.toString();
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam("login") String login,@RequestParam("password") String password  )
			throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = userService.login(login,password);
		
		if(user!=null) {
			writer = new ParserConfig().getWriter();
			
			mapper.writeValue(writer, user);
			return writer.toString();
		}
		else
			return null;
		
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(@RequestParam("login") String login) {

		if (userService.deleteUser(login))
			return "User deleted successfully";
		else
			return "Error occured !";
	}

	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	@ResponseBody
	public String createUser(@RequestParam("login") String login, @RequestParam("name") String name,
			@RequestParam("password") String password) {

		if (userService.createUser(login, name, password))
			return "1";
		else
			return "0";
	}

	@RequestMapping(value = "/createusertable", method = RequestMethod.POST)
	@ResponseBody
	public String createUserTable(@RequestParam("tablename") String tableName) {
		
		if(!userService.createUserTable(tableName)) return "Table created successfully";
		else return "Error occured !";
	}

	@RequestMapping(value = "/dropusertable", method = RequestMethod.POST)
	@ResponseBody
	public String dropUserTable(@RequestParam("tablename") String tableName) {

		if(!userService.dropUserTable(tableName)) return "Table dropped successfully";
		else return "Error occured !";
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/createUserSharedTablesTable", method = RequestMethod.POST)
	@ResponseBody
	public String createUserSharedTablesTable(@RequestParam("tablename") String tableName) {
		if(userService.createUserSharedTablesTable(tableName)) return "Table created successfully";
		else return "Error occured !";
	}
	@RequestMapping(value = "/insertIntoUserSharedTablesTable", method = RequestMethod.POST)
	@ResponseBody
	public String insertIntoUserSharedTablesTable(@RequestParam("tablename") String tableName,@RequestParam("name") String name,@RequestParam("hiddenname")String hiddenName,@RequestParam("password")String password,@RequestParam("firstOwner")String firstOwner) {
		if(userService.insertIntoUserSharedTablesTable(tableName,name,hiddenName,password,firstOwner)) return "Object added successfully";
		else return "Error occured !";
	}
	@RequestMapping(value = "/deleteFromUserSharedTablesTable", method = RequestMethod.POST)
	@ResponseBody
	public String deleteFromUserSharedTablesTable(@RequestParam("tablename") String tableName,@RequestParam("hiddenname") String hiddenName) {
		if(userService.deleteFromUserSharedTablesTable(tableName, hiddenName)) return "Object deleted successfully";
		else return "Error occured !";
	}
	
	
	
}
