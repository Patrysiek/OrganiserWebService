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

	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(@RequestParam("login") String login) {

		if (userService.deleteUser(login))
			return "Udalo sie usunac uzytkownika";
		else
			return "Wystapil blad";
	}

	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	@ResponseBody
	public String createUser(@RequestParam("login") String login, @RequestParam("name") String name,
			@RequestParam("password") String password) {

		if (userService.createUser(login, name, password))
			return "Udalo sie stworzyc uzytkownika";
		else
			return "Wystapil blad";
	}

	@RequestMapping(value = "/createusertable", method = RequestMethod.POST)
	@ResponseBody
	public boolean createUserTable(@RequestParam("tablename") String tableName) {
		return userService.createUserTable(tableName);
	}

	@RequestMapping(value = "/dropusertable", method = RequestMethod.POST)
	@ResponseBody
	public boolean dropUserTable(@RequestParam("tablename") String tableName) {
		return userService.dropUserTable(tableName);
	}

}
