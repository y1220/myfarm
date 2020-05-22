package it.course.myfarm.payload.response;

import it.course.myfarm.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

	private String empNo;

	private String firstName;

	private String lastName;

	private char gender;

	public static EmployeeResponse create(Employee e) {
		return new EmployeeResponse(e.getEmpNo(), e.getFirstName(), e.getLastName(), e.getGender());

	}

}
