package it.course.myfarm.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salary")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "SalaryCompositeKey")
@JsonIdentityReference(alwaysAsId = true)
public class Salary implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalaryCompositeKey salaryCompositeKey;

	@Column(name = "salary", nullable = false, columnDefinition = "INT(11)")
	private int salary;

	@Column(name = "toDate", nullable = true)
	private Date toDate;

}
