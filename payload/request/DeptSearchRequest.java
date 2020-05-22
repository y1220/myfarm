package it.course.myfarm.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DeptSearchRequest {

	List<char[]> deptNos;

	public List<char[]> getDeptNos() {
		return deptNos;
	}

	public void setDeptNos(List<char[]> deptNos) {
		this.deptNos = deptNos;
	}
}
