package it.course.myfarm.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchTitlesByEmpNoResponse {

	// private Employee employee;

	private EmployeeResponse eResponse;

	// private List<Titles> tlist;

	private List<TitlesResponse> trlist;
}
