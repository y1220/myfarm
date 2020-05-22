package it.course.myfarm.controller;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.course.myfarm.entity.Employee;
import it.course.myfarm.entity.Salary;
import it.course.myfarm.entity.SalaryCompositeKey;
import it.course.myfarm.payload.request.SalaryRequest;
import it.course.myfarm.payload.response.ApiResponseCustom;
import it.course.myfarm.payload.response.AvgSalaryOfEveryoneResponse;
import it.course.myfarm.payload.response.SalaryOfEveryoneResponse;
import it.course.myfarm.repository.EmployeeRepository;
import it.course.myfarm.repository.SalaryRepository;

@RestController
@RequestMapping("/salary")
public class SalaryController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SalaryRepository salaryRepository;

	@PostMapping("/insert-salary")
	public ResponseEntity<ApiResponseCustom> insertSalary(@RequestBody SalaryRequest sRequest,
			HttpServletRequest request) {

		Optional<Employee> e = employeeRepository.findByEmpNo(sRequest.getEmpNo());

		Optional<Salary> sPrevious = salaryRepository.findBySalaryCompositeKeyEmpNoAndToDateIsNull(e.get());
		if (sPrevious.isPresent()) {
			// Date today = (Date) Calendar.getInstance().getTime();
			// sPrevious.get().setToDate(today);
			sPrevious.get().setToDate(sRequest.getFromDate());
			salaryRepository.save(sPrevious.get());
		}
		Salary s = new Salary();

		SalaryCompositeKey sck = new SalaryCompositeKey(e.get(), sRequest.getFromDate());
		s.setSalaryCompositeKey(sck);
		s.setSalary(sRequest.getSalary());
		salaryRepository.save(s);

		return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 200, null,
				"New salary record successfully created", request.getRequestURI()), HttpStatus.OK);
	}

	// AVERAGE ACTIVE SALARY FOR ALL EMPLOYEE
	@GetMapping("/average-salary")
	public ResponseEntity<ApiResponseCustom> lastSalaryByEmployee(HttpServletRequest request) {

		List<Employee> elist = employeeRepository.findAll();
		if (elist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		int cnt = 0, avg = 0;
		for (Employee e : elist) {
			Optional<Salary> s = salaryRepository.findBySalaryCompositeKeyEmpNoAndToDateIsNull(e);
			if (s.isPresent()) {
				avg = avg + s.get().getSalary();
				cnt++;
			}
		}
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, (long) avg / (long) cnt, request.getRequestURI()),
				HttpStatus.OK);

	}

	@GetMapping("/salary-of-everyone")
	public ResponseEntity<ApiResponseCustom> salaryOfEveryone(HttpServletRequest request) {

		List<Employee> elist = employeeRepository.findAll();
		if (elist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		Set<SalaryOfEveryoneResponse> sSet = new HashSet<SalaryOfEveryoneResponse>();
		// COLLECT THE CURRENT SALARY FOR EACH EMPLOYEE
		for (Employee e : elist) {
			Optional<Salary> s = salaryRepository.findBySalaryCompositeKeyEmpNoAndToDateIsNull(e);
			if (s.isPresent()) {
				SalaryOfEveryoneResponse sr = new SalaryOfEveryoneResponse(e.getFirstName(), e.getLastName(),
						s.get().getSalary());
				sSet.add(sr);
			}
		}
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, sSet, request.getRequestURI()), HttpStatus.OK);

	}

	// AVERAGE SALARY FROM EACH HISTORY
	@GetMapping("/average-salary-of-everyone")
	public ResponseEntity<ApiResponseCustom> averageSalaryOfEveryone(HttpServletRequest request) {

		List<Employee> elist = employeeRepository.findAll();
		if (elist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		Set<AvgSalaryOfEveryoneResponse> sSet = new HashSet<AvgSalaryOfEveryoneResponse>();
		// COLLECT THE CURRENT SALARY FOR EACH EMPLOYEE
		for (Employee e : elist) {
			List<Salary> slist = salaryRepository.findBySalaryCompositeKeyEmpNo(e);
			if (!slist.isEmpty()) {
				int cnt = 0, avg = 0;
				for (Salary s : slist) {
					avg = avg + s.getSalary();
					cnt++;
				}
				long avgFinal = (long) avg / (long) cnt;
				AvgSalaryOfEveryoneResponse sr = new AvgSalaryOfEveryoneResponse(e.getFirstName(), e.getLastName(),
						avgFinal);
				sSet.add(sr);
			}
		}
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, sSet, request.getRequestURI()), HttpStatus.OK);

	}
}
