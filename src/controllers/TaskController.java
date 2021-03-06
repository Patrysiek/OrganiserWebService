package controllers;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import configFiles.ParserConfig;
import services.TaskService;

@Controller
@ComponentScan("/configFiles")
public class TaskController {
	
	private TaskService taskService;
	private StringWriter stringWriter;
	private ObjectMapper objectMapper;
	
	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	
	@RequestMapping(value="/alltasks",method=RequestMethod.GET)
	@ResponseBody
	public String getAllTasks(@RequestParam("tablename")String tableName) throws JsonGenerationException, JsonMappingException, IOException{
		
		stringWriter = new ParserConfig().getWriter();
		objectMapper.writeValue(stringWriter,taskService.getAllTasks(tableName));
		
		return stringWriter.toString();
	}
	
	@RequestMapping(value="/alltasksfromday",method=RequestMethod.POST)
	@ResponseBody
	public String getAllTasksFromDay(@RequestParam("tablename")String tableName,@RequestParam("date")String date) throws JsonGenerationException, JsonMappingException, IOException{
		
		stringWriter = new ParserConfig().getWriter();
		objectMapper.writeValue(stringWriter,taskService.getAllTaskFromDay(tableName, date));
		
		return stringWriter.toString();
	}
	@RequestMapping(value="/onetask",method=RequestMethod.POST)
	@ResponseBody
	public String getTask(@RequestParam("tablename")String tableName,@RequestParam("ID")int ID) throws JsonGenerationException, JsonMappingException, IOException{
		stringWriter = new ParserConfig().getWriter();
		objectMapper.writeValue(stringWriter,taskService.getTask(tableName, ID));
		
		return stringWriter.toString();
	}
	
	@RequestMapping(value="/createtasktable",method=RequestMethod.POST)
	@ResponseBody
	public void createTaskTable(@RequestParam("tablename")String tableName){
		taskService.createTaskTable(tableName);
	}
	@RequestMapping(value="/droptasktable",method=RequestMethod.POST)
	@ResponseBody
	public void dropTaskTable(@RequestParam("tablename")String tableName){
		taskService.dropTable(tableName);
	}
	@RequestMapping(value="/inserttask",method=RequestMethod.POST)
	@ResponseBody
	public void insertTask(@RequestParam("tablename")String tableName,@RequestParam("date")String date,@RequestParam("description")String description,@RequestParam("status")String status){
		taskService.insertTask(tableName, date, description,status);
	}
	@RequestMapping(value="/deletetask",method=RequestMethod.POST)
	@ResponseBody
	public void deleteTask(@RequestParam("tablename")String tableName,@RequestParam("ID")String IDs){
		taskService.deleteTask(tableName, IDs);
	}
	@RequestMapping(value="/updatetask",method=RequestMethod.POST)
	@ResponseBody
	public void updateTask(@RequestParam("tablename")String tableName,@RequestParam("ID")String ID,@RequestParam("status")String status){
		taskService.updateTask(tableName, ID,status);
	}

}
