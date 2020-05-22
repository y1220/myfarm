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
@Table(name = "dept_emp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "DeptEmpCompositeKey")
@JsonIdentityReference(alwaysAsId = true)
public class DeptEmp implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DeptEmpCompositeKey deptEmpCompositeKey;

	@Column(name = "fromDate", nullable = false)
	private Date fromDate;

	@Column(name = "toDate", nullable = true)
	private Date toDate;

}
