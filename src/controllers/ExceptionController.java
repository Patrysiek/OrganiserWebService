package controllers;


import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

	@ResponseBody
	@ExceptionHandler(DataAccessException.class)
	public String exceptionHandler(DataAccessException ex) {
		return "0";
	}
	
}
