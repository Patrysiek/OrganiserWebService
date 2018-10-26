package tasks;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TaskRowMapper implements RowMapper<Task>{

	@Override
	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new Task(rs.getInt("ID"), rs.getDate("date"), rs.getString("description"));
	}

}
