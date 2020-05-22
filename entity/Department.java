package it.course.myfarm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

	@Id
	// @Size(max = 4)
	@Column(name = "dept_no", nullable = false, columnDefinition = "CHAR(4)")
	private char[] deptNo;

	@NaturalId(mutable = true)
	@Column(name = "deptName", nullable = false, columnDefinition = "VARCHAR(40)")
	private String deptName;
}
