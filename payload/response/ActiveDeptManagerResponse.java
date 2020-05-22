package it.course.myfarm.payload.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveDeptManagerResponse {

	private String firstName;

	private String lastName;

	private String departmentName;

	private Date frodate;
}
