package it.course.myfarm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgSalaryOfEveryoneResponse {

	private String firstName;

	private String lastName;

	private long salary;
}
