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
@Table(name = "dept_manager")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "DeptManagerCompositeKey")
@JsonIdentityReference(alwaysAsId = true)
public class DeptManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DeptManagerCompositeKey deptManagerCompositeKey;

	@Column(name = "fromDate", nullable = false)
	private Date fromDate;

	@Column(name = "toDate", nullable = true)
	private Date toDate;

}
