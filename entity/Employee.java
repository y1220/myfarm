package it.course.myfarm.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	// @Size(max = 6)
	@Column(name = "emp_no", nullable = false, columnDefinition = "VARCHAR(6)")
	private String empNo;

	@Column(name = "birth_date", nullable = false)
	private Date birthDate;

	@Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(50)")
	private String firstName;

	@Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(50)")
	private String lastName;

	@Column(name = "gender", nullable = false, columnDefinition = "CHAR(1)")
	private char gender;

	@Column(name = "hire_date", nullable = false)
	private Date hireDate;

}
