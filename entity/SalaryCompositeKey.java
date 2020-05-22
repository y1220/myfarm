package it.course.myfarm.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryCompositeKey implements Serializable {

	@ManyToOne
	@JoinColumn(name = "emp_no")
	private Employee empNo;

	@NotNull
	private Date fromDate;

	private static final long serialVersionUID = 1L;

}
