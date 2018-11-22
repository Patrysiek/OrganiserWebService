package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Table;
import table.TableDAO;

@Service("tableService")
public class TableService {

	TableDAO tableDAO;

	@Autowired
	public void setTableDAO(TableDAO tableDAO) {
		this.tableDAO = tableDAO;
	}

	public List<Table> getAllTablesFromSharedTables() {
		return tableDAO.getAllTablesFromAllSharedTables();
	}

	public List<Table> getUserAllSharedTables(String tablename) {
		return tableDAO.getUserAllSharedTables(tablename);
	}
	public List<Table> getParticularSharedTables(String hiddenName,String password) {
		return tableDAO.getParticularTablesFromAllSharedTables(hiddenName,password);
	}
	

	public String addNewTableToAllSharedTablesAndToFirstOwnerTable(String firstOwnerTableName,String name, String password,String firstOwner) {
		return tableDAO.addNewTableToAllSharedTablesAndToFirstOwnerTable(firstOwnerTableName,name, password,firstOwner);
	}
	public boolean dropSharedTable(String hiddenName) {
		return tableDAO.dropSharedTable(hiddenName);
	}
	public boolean createUserTable(String tableName) {
		return tableDAO.createUserTable(tableName);
	}

	public boolean tableDAO(String tableName) {
		return tableDAO.dropUserTable(tableName);
	}
	public boolean createUserSharedTablesTable(String tableName) {
		return tableDAO.createUserSharedTablesTable(tableName);
	}
	public boolean deleteFromUserSharedTablesTable(String tableName,String hiddenName) {
		return tableDAO.deleteFromUserSharedTablesTable(tableName,hiddenName);
	}
	public boolean insertIntoUserSharedTablesTable(String tableName,String name,String hiddenName,String password,String firstOwner) {
		return tableDAO.insertIntoUserSharedTablesTable(tableName,name,hiddenName,password,firstOwner);
	}

	public boolean dropUserTable(String tableName) {
		return tableDAO.dropUserTable(tableName);
	}

}
