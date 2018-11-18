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
		return tableDAO.getAllTablesFromSharedTables();
	}

	public List<Table> getUserAllSharedTables(String tablename) {
		return tableDAO.getUserAllSharedTables(tablename);
	}
	public List<Table> getParticularSharedTables(String hiddenName,String password) {
		return tableDAO.getParticularTablesFromSharedTables(hiddenName,password);
	}
	

	public String addNewTableToSharedTables(String name, String password) {
		return tableDAO.addNewTableToSharedTables(name, password);
	}
	public boolean deleteSharedTable(String hiddenName) {
		return tableDAO.deleteSharedTable(hiddenName);
	}

}
