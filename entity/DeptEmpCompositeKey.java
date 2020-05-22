package it.course.myfarm.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptEmpCompositeKey implements Serializable {

	@ManyToOne
	@JoinColumn(name = "emp_no")
	private Employee empNo;

	@ManyToOne
	@JoinColumn(name = "dept_no")
	private Department deptNo;

	private static final long serialVersionUID = 1L;

}
