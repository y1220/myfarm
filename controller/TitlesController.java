package it.course.myfarm.controller;

import java.time.Instant;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.course.myfarm.entity.Employee;
import it.course.myfarm.entity.Titles;
import it.course.myfarm.entity.TitlesCompositeKey;
import it.course.myfarm.payload.request.TitlesRequest;
import it.course.myfarm.payload.response.ApiResponseCustom;
import it.course.myfarm.repository.EmployeeRepository;
import it.course.myfarm.repository.TitlesRepository;

@RestController
@RequestMapping("/titles")
public class TitlesController {

	@Autowired
	TitlesRepository titlesRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping("/insert-title")
	public ResponseEntity<ApiResponseCustom> insertEmployee(@RequestBody TitlesRequest tRequest,
			HttpServletRequest request) {

		Optional<Employee> e = employeeRepository.findByEmpNo(tRequest.getEmpNo());

		Optional<Titles> tPrevious = titlesRepository.findByTitlesCompositeKeyEmpNoAndToDateIsNull(e.get());
		if (tPrevious.isPresent()) {
			// Date today = (Date) Calendar.getInstance().getTime();
			// tPrevious.get().setToDate(today);
			tPrevious.get().setToDate(tRequest.getFromDate());
			titlesRepository.save(tPrevious.get());
		}
		Titles t = new Titles();

		TitlesCompositeKey tck = new TitlesCompositeKey(e.get(), tRequest.getTitle(), tRequest.getFromDate());
		t.setTitlesCompositeKey(tck);
		titlesRepository.save(t);

		return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 200, null,
				"New title successfully created", request.getRequestURI()), HttpStatus.OK);
	}

}
