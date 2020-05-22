package it.course.myfarm.payload.request;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitlesRequest {

	@NotNull
	private String empNo;

	@NotNull
	private String title;

	@NotNull
	private Date fromDate;

}
