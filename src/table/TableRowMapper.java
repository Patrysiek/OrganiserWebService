package table;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TableRowMapper implements RowMapper<Table>{

	@Override
	public Table mapRow(ResultSet rs, int rowNum) throws SQLException {

		return new Table(rs.getInt("ID"), rs.getString("name"), rs.getString("password"),rs.getString("hiddenName"),rs.getString("firstOwner"));
	}

}
