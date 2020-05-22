package it.course.myfarm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryOfEveryoneResponse {

	private String firstName;

	private String lastName;

	private int salary;

//	private static String summary(Employee e, Salary s) {
//		return "fullname: " + e.getFirstName() + e.getLastName() + " Salary: " + s.getSalary();
//	}
}
