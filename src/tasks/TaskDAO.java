package tasks;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("TaskDAO")
public class TaskDAO {

private JdbcTemplate jdbcTemplate;

@Autowired
public JdbcTemplate setJdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);
}



}
