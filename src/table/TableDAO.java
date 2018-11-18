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

	public List<Table> getAllTablesFromSharedTables() {
		return jdbcTemplate.query("SELECT * FROM all_shared_tables", new TableRowMapper());
	}
	public List<Table> getParticularTablesFromSharedTables(String hiddenName,String password) {
		return jdbcTemplate.query("SELECT * FROM all_shared_tables WHERE hiddenName = ? AND password = ?",new TableRowMapper(),hiddenName,password);
	}

	public List<Table> getUserAllSharedTables(String tablename) {
		return jdbcTemplate.query("SELECT * FROM " + tablename, new TableRowMapper());
	}

	public String addNewTableToSharedTables(String name, String password) {

		int id = getMaxID();
		String hiddenName = name + String.valueOf(id);

		if (jdbcTemplate.update("INSERT INTO all_shared_tables(name,password,hiddenName)VALUES(?,?,?)", name, password,
				hiddenName) == 1) {
			if (createNewSharedTable(hiddenName))
				return "Success";
			else
				return "Error";
		} else {
			return "Error";
		}
	}
	private int getMaxID() {
		try {
			return jdbcTemplate.queryForObject("SELECT MAX(ID) FROM all_shared_tables", Integer.class);
		}catch(Exception e) {
			return 0;
		}
		
	}
	private boolean createNewSharedTable(String hiddenName) {
		String sql = "create table " + hiddenName
				+ "(ID int auto_increment not null primary key,opis varchar(200),data date)";
		return jdbcTemplate.update(sql) == 1;
	}

	public boolean deleteSharedTable(String hiddenName) {
		String sql = "drop table " + hiddenName;
		return jdbcTemplate.update(sql) == 1;
	}

}
