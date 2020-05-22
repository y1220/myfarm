package it.course.myfarm.payload.request;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptEmpRequest {

	@NotNull
	private String empNo;

	@NotNull
	private char[] deptNo;

	@NotNull
	private Date fromDate;

}
