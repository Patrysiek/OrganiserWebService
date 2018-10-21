package configFiles;

import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfig {

	@Bean
	public ObjectMapper getMapper() {
		return new ObjectMapper();
	}
	@Bean
	public StringWriter getWriter() {
		return new StringWriter();
	}
}
