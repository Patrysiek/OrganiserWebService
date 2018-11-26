package table;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("tableDAO")
public class TableDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Table> getAllTablesFromAllSharedTables() {
		return jdbcTemplate.query("SELECT * FROM all_shared_tables", new TableRowMapper());
	}

	public List<Table> getParticularTablesFromAllSharedTables(String hiddenName, String password) {
		return jdbcTemplate.query("SELECT * FROM all_shared_tables WHERE hiddenName = ? AND password = ?",
				new TableRowMapper(), hiddenName, password);
	}

	public List<Table> getUserAllSharedTables(String tablename) {
		return jdbcTemplate.query("SELECT * FROM " + tablename, new TableRowMapper());
	}

	public String addNewTableToAllSharedTablesAndToFirstOwnerTable(String firstOwnerTableName, String name,
			String password, String firstOwner) {

		int id = getMaxID();
		String hiddenName = String.valueOf(id) + name;

		try {
			if (jdbcTemplate.update("INSERT INTO all_shared_tables(name,password,hiddenName,firstOwner)VALUES(?,?,?,?)",
					name, password, hiddenName, firstOwner) == 1) {
				if (jdbcTemplate.update(
						"INSERT INTO " + firstOwnerTableName + "(name,password,hiddenName,firstOwner)VALUES(?,?,?,?)",
						name, password, hiddenName, firstOwner) == 1) {
					if (createNewSharedTable(hiddenName))
						return "Success";
					else
						return "Error";
				} else
					return "Error";
			} else
				return "Error";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

	}

	private int getMaxID() {
		try {
			return jdbcTemplate.queryForObject("SELECT MAX(ID) FROM all_shared_tables", Integer.class);
		} catch (Exception e) {
			return 0;
		}

	}

	private boolean createNewSharedTable(String hiddenName) {
		String sql = "CREATE TABLE IF NOT EXISTS "+hiddenName+"(ID int primary key NOT NULL  auto_increment,Date date,description varchar(40),status varchar(10))";
		return jdbcTemplate.update(sql) == 1;
	}
	public boolean deleteTableFromAllSharedTables(String hiddenName) {
		try {
		String sql = "DELETE FROM all_shared_tables WHERE hiddenName = '" + hiddenName+"'";
		return jdbcTemplate.update(sql) == 1;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean createUserTable(String tableName) {
		try {
			String sql = "create table "+tableName+"(ID int auto_increment not null primary key,opis varchar(200),data date)";
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean createUserSharedTablesTable(String tableName) {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" (ID int(11) NOT NULL AUTO_INCREMENT,name varchar(45) NOT NULL,password varchar(45) NOT NULL,hiddenName varchar(45) NOT NULL,firstOwner varchar(45) NOT NULL,PRIMARY KEY (ID),UNIQUE KEY ID_UNIQUE (ID),UNIQUE KEY hiddenName_UNIQUE (hiddenName))";
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean deleteFromUserSharedTablesTable(String tableName,String hiddenName) {
		try {
			String sql = "DELETE FROM "+tableName+" WHERE hiddenName = '"+hiddenName+"'";
			jdbcTemplate.update(sql);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean insertIntoUserSharedTablesTable(String tableName,String name,String hiddenName,String password,String firstOwner) {
		try {
			String sql = "insert into "+tableName+"(name,password,hiddenName,firstOwner)VALUES(?,?,?,?)";
			jdbcTemplate.update(sql,name,password,hiddenName,firstOwner);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean dropTable(String tableName) {
		String sql = "drop table "+tableName;
		
		try {
		return jdbcTemplate.update(sql)==1;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	

}
