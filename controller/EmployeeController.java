package it.course.myfarm.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.course.myfarm.entity.Department;
import it.course.myfarm.entity.DeptEmp;
import it.course.myfarm.entity.DeptManager;
import it.course.myfarm.entity.Employee;
import it.course.myfarm.entity.Salary;
import it.course.myfarm.entity.Titles;
import it.course.myfarm.payload.request.SearchTitlesByEmpNoRequest;
import it.course.myfarm.payload.response.ApiResponseCustom;
import it.course.myfarm.payload.response.EmployeeResponse;
import it.course.myfarm.payload.response.SearchTitlesByEmpNoResponse;
import it.course.myfarm.payload.response.TitlesResponse;
import it.course.myfarm.repository.DepartmentRepository;
import it.course.myfarm.repository.DeptEmpRepository;
import it.course.myfarm.repository.DeptManagerRepository;
import it.course.myfarm.repository.EmployeeRepository;
import it.course.myfarm.repository.SalaryRepository;
import it.course.myfarm.repository.TitlesRepository;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DeptEmpRepository deptEmpRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DeptManagerRepository deptManagerRepository;

	@Autowired
	SalaryRepository salaryRepository;

	@Autowired
	TitlesRepository titlesRepository;

	@PostMapping("/insert-employee")
	public ResponseEntity<ApiResponseCustom> insertEmployee(@RequestBody Employee employee,
			HttpServletRequest request) {

		Employee e = new Employee();
		e.setEmpNo(employee.getEmpNo());
		e.setBirthDate(employee.getBirthDate());
		e.setFirstName(employee.getFirstName());
		e.setLastName(employee.getLastName());
		e.setGender(employee.getGender());
		e.setHireDate(employee.getHireDate());

		employeeRepository.save(e);

		return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 200, null,
				"New employee data successfully created", request.getRequestURI()), HttpStatus.OK);
	}

	@GetMapping("/view-all-employee")
	public ResponseEntity<ApiResponseCustom> viewAllEmployee(HttpServletRequest request) {

		List<Employee> allEmployee = employeeRepository.findAll();
		if (allEmployee.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		// TRANSFORM INTO DESC
		List<Employee> sorted = allEmployee.stream().sorted(Comparator.comparing(Employee::getEmpNo).reversed())
				.collect(Collectors.toList());

		// MAKES LIST EASY TO READ
		List<EmployeeResponse> all = new ArrayList<EmployeeResponse>();
		for (Employee e : sorted) {
			EmployeeResponse er = EmployeeResponse.create(e);
			all.add(er);
		}

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, all, request.getRequestURI()), HttpStatus.OK);

	}

	@GetMapping("/view-employee/{id}")
	public ResponseEntity<ApiResponseCustom> viewAllEmployee(@PathVariable String id, HttpServletRequest request) {

		Optional<Employee> e = employeeRepository.findByEmpNo(id);
		if (!e.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		EmployeeResponse er = EmployeeResponse.create(e.get());

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, er, request.getRequestURI()), HttpStatus.OK);

	}

	@GetMapping("/view-department-by-employee/{id}")
	public ResponseEntity<ApiResponseCustom> viewDepartmentByEmployee(@PathVariable String id,
			HttpServletRequest request) {

		Optional<Employee> e = employeeRepository.findByEmpNo(id);
		if (!e.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		Set<Department> dlist = new HashSet<Department>();

		// SEARCH ROLE BOTH IN MANAGER AND EMPLOYEE
		List<DeptManager> dmList = deptManagerRepository.findByDeptManagerCompositeKeyEmpNo(e.get());
		for (DeptManager dm : dmList) {
			Optional<Department> d = departmentRepository
					.findByDeptNo(dm.getDeptManagerCompositeKey().getDeptNo().getDeptNo());
			dlist.add(d.get());
		}
		List<DeptEmp> deList = deptEmpRepository.findByDeptEmpCompositeKeyEmpNo(e.get());
		for (DeptEmp de : deList) {
			Optional<Department> d = departmentRepository
					.findByDeptNo(de.getDeptEmpCompositeKey().getDeptNo().getDeptNo());
			dlist.add(d.get());
		}

		if (dlist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"No department history not found for this employee", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, dlist, request.getRequestURI()), HttpStatus.OK);

	}

	@GetMapping("/last-salary-by-employee/{id}")
	public ResponseEntity<ApiResponseCustom> lastSalaryByEmployee(@PathVariable String id, HttpServletRequest request) {

		Optional<Employee> e = employeeRepository.findByEmpNo(id);
		if (!e.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		Optional<Salary> s = salaryRepository.findBySalaryCompositeKeyEmpNoAndToDateIsNull(e.get());
		if (!s.isPresent())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"Employee's salary not found", request.getRequestURI()), HttpStatus.NOT_FOUND);

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, s.get().getSalary(), request.getRequestURI()),
				HttpStatus.OK);

	}

	// public ResponseEntity<ApiResponseCustom> titlesByLastname(@PathVariable
	// List<String> sten,
	@GetMapping("/titles-by-lastname")
	public ResponseEntity<ApiResponseCustom> titlesByLastname(@RequestBody SearchTitlesByEmpNoRequest sten,
			HttpServletRequest request) {

		List<Employee> elist = employeeRepository.findByEmpNoIn(sten.getEmpNos());

		// TRANSFORM INTO ASCE DEPENDS ON LASTNAME
		List<Employee> eSorted = elist.stream().sorted(Comparator.comparing(Employee::getLastName))
				.collect(Collectors.toList());
		if (eSorted.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		List<SearchTitlesByEmpNoResponse> res = new ArrayList<SearchTitlesByEmpNoResponse>();
		for (Employee e : eSorted) {
			List<Titles> tlist = titlesRepository.findByTitlesCompositeKeyEmpNo(e);
			if (!tlist.isEmpty()) {
				List<TitlesResponse> trlist = TitlesResponse.createList(tlist);
				EmployeeResponse er = EmployeeResponse.create(e);
				SearchTitlesByEmpNoResponse ster = new SearchTitlesByEmpNoResponse(er, trlist);
				res.add(ster);
			}
		}
		if (res.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Titles not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, res, request.getRequestURI()), HttpStatus.OK);

	}

}
