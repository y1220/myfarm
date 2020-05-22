package it.course.myfarm.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "titles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Titles implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TitlesCompositeKey titlesCompositeKey;

	@Column(name = "toDate", nullable = true)
	private Date toDate;

}
