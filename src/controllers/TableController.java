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
import services.TableService;

@Controller
@ComponentScan("/configFiles")
public class TableController {
	
	private TableService tableService;
	private StringWriter stringWriter;
	private ObjectMapper objectMapper;
	
	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Autowired
	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}
	
	
	@RequestMapping(value="/AllTablesFromSharedTable",method=RequestMethod.POST)
	@ResponseBody
	public String getAllTablesFromSharedTables() throws JsonGenerationException, JsonMappingException, IOException{
		
		stringWriter = new ParserConfig().getWriter();
		objectMapper.writeValue(stringWriter,tableService.getAllTablesFromSharedTables());
		return stringWriter.toString();
	}
	
	@RequestMapping(value="/userAllSharedTables",method=RequestMethod.POST)
	@ResponseBody
	public String userAllSharedTables(@RequestParam("tablename")String tableName) throws JsonGenerationException, JsonMappingException, IOException{
		
		stringWriter = new ParserConfig().getWriter();
		objectMapper.writeValue(stringWriter,tableService.getUserAllSharedTables(tableName));
		
		return stringWriter.toString();
	}
	@RequestMapping(value="/particularSharedTables",method=RequestMethod.POST)
	@ResponseBody
	public String particularSharedTables(@RequestParam("hiddenname")String hiddenName,@RequestParam("password")String password) throws JsonGenerationException, JsonMappingException, IOException{
		
		stringWriter = new ParserConfig().getWriter();
		objectMapper.writeValue(stringWriter,tableService.getParticularSharedTables(hiddenName, password));
		
		return stringWriter.toString();
	}
	
	@RequestMapping(value="/deleteSharedTable",method=RequestMethod.POST)
	@ResponseBody
	public boolean getTask(@RequestParam("tablename")String hiddenName) throws JsonGenerationException, JsonMappingException, IOException{
		
		return tableService.dropSharedTable(hiddenName);
	}
	
	@RequestMapping(value="/newTableToSharedTables",method=RequestMethod.POST)
	@ResponseBody
	public void addNewTableToAllSharedTablesAndToFirstOwnerTable( @RequestParam("tablename")String tableName,@RequestParam("password")String password,@RequestParam("firstOwner")String firstOwner, @RequestParam("firstOwnerTablename")String firstOwnerTableName){
		tableService.addNewTableToAllSharedTablesAndToFirstOwnerTable(firstOwnerTableName,tableName, password,firstOwner);
	}
	@RequestMapping(value = "/createusertable", method = RequestMethod.POST)
	@ResponseBody
	public String createUserTable(@RequestParam("tablename") String tableName) {
		
		if(!tableService.createUserTable(tableName)) return "Table created successfully";
		else return "Error occured !";
	}

	@RequestMapping(value = "/dropusertable", method = RequestMethod.POST)
	@ResponseBody
	public String dropUserTable(@RequestParam("tablename") String tableName) {

		if(!tableService.dropUserTable(tableName)) return "Table dropped successfully";
		else return "Error occured !";
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/createUserSharedTablesTable", method = RequestMethod.POST)
	@ResponseBody
	public String createUserSharedTablesTable(@RequestParam("tablename") String tableName) {
		if(tableService.createUserSharedTablesTable(tableName)) return "Table created successfully";
		else return "Error occured !";
	}
	@RequestMapping(value = "/insertIntoUserSharedTablesTable", method = RequestMethod.POST)
	@ResponseBody
	public String insertIntoUserSharedTablesTable(@RequestParam("tablename") String tableName,@RequestParam("name") String name,@RequestParam("hiddenname")String hiddenName,@RequestParam("password")String password,@RequestParam("firstOwner")String firstOwner) {
		if(tableService.insertIntoUserSharedTablesTable(tableName,name,hiddenName,password,firstOwner)) return "Object added successfully";
		else return "Error occured !";
	}
	@RequestMapping(value = "/deleteFromUserSharedTablesTable", method = RequestMethod.POST)
	@ResponseBody
	public String deleteFromUserSharedTablesTable(@RequestParam("tablename") String tableName,@RequestParam("hiddenname") String hiddenName) {
		if(tableService.deleteFromUserSharedTablesTable(tableName, hiddenName)) return "Object deleted successfully";
		else return "Error occured !";
	}
	
}
