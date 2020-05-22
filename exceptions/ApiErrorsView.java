package it.course.myfarm.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
public class ApiErrorsView {
	
	List<ApiFieldError> fieldErrors;
	List<ApiGlobalError> globalErrors;

}
