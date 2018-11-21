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

	public boolean deleteSharedTable(String hiddenName) {
		String sql = "drop table " + hiddenName;
		return jdbcTemplate.update(sql) == 1;
	}

	public boolean deleteFromAllSharedTables(String hiddenName) {
		String sql = "DELETE FROM all_shared_tables WHERE hiddenName = " + hiddenName;
		return jdbcTemplate.update(sql) == 1;
	}

}
