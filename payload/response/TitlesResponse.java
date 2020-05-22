package it.course.myfarm.payload.response;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import it.course.myfarm.entity.Titles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitlesResponse {

	private String titles;

	private Date fromDate;

	public static TitlesResponse create(Titles t) {
		return new TitlesResponse(t.getTitlesCompositeKey().getTitle(), t.getTitlesCompositeKey().getFromDate());

	}

	public static List<TitlesResponse> createList(List<Titles> tlist) {
//		CHECK BEFORE ENTERING THIS MRTHOD
//		if(tlist.isEmpty())
//			return null;
		List<TitlesResponse> trlist = new ArrayList<TitlesResponse>();
		for (Titles t : tlist)
			trlist.add(TitlesResponse.create(t));
		return trlist;

	}

}
