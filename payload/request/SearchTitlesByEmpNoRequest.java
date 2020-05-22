package it.course.myfarm.payload.request;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SearchTitlesByEmpNoRequest {

	List<String> empNos;

	public List<String> getEmpNos() {
		return empNos;
	}

	public void setEmpNos(List<String> empNos) {
		this.empNos = empNos;
	}
}
