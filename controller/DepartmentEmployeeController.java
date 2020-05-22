package it.course.myfarm.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.course.myfarm.entity.Department;
import it.course.myfarm.entity.DeptEmp;
import it.course.myfarm.entity.DeptEmpCompositeKey;
import it.course.myfarm.entity.Employee;
import it.course.myfarm.payload.request.DeptEmpRequest;
import it.course.myfarm.payload.request.DeptSearchRequest;
import it.course.myfarm.payload.response.ActiveDeptManagerResponse;
import it.course.myfarm.payload.response.ApiResponseCustom;
import it.course.myfarm.repository.DepartmentRepository;
import it.course.myfarm.repository.DeptEmpRepository;
import it.course.myfarm.repository.EmployeeRepository;

@RestController
@RequestMapping("/department-employee")
public class DepartmentEmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DeptEmpRepository deptEmpRepository;

	@PostMapping("/insert-department-employee")
	public ResponseEntity<ApiResponseCustom> insertDepartment(@RequestBody DeptEmpRequest deRequest,
			HttpServletRequest request) {

		Optional<Employee> e = employeeRepository.findByEmpNo(deRequest.getEmpNo());
		if (!e.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		Optional<Department> d = departmentRepository.findByDeptNo(deRequest.getDeptNo());
		if (!d.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Department not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		DeptEmpCompositeKey deck = new DeptEmpCompositeKey(e.get(), d.get());
		DeptEmp de = new DeptEmp();
		de.setDeptEmpCompositeKey(deck);
		de.setFromDate(deRequest.getFromDate());
		deptEmpRepository.save(de);

		return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 200, null,
				"New department employee data successfully created", request.getRequestURI()), HttpStatus.OK);
	}

	@GetMapping("/all-employees-by-deptNo")
	public ResponseEntity<ApiResponseCustom> allEmployeesByDeptNo(@RequestBody DeptSearchRequest dmr,
			HttpServletRequest request) {

		List<Department> dlist = departmentRepository.findByDeptNoIn(dmr.getDeptNos());
		if (dlist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"No department is registered", request.getRequestURI()), HttpStatus.NOT_FOUND);

		// TRANSFORM IT INTO READABLE SHAPE
		List<DeptEmp> delist = deptEmpRepository.findByDeptEmpCompositeKeyDeptNoInAndToDateIsNull(dlist);
		if (delist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"No employee is registered for these departments", request.getRequestURI()), HttpStatus.NOT_FOUND);
		List<ActiveDeptManagerResponse> res = new ArrayList<ActiveDeptManagerResponse>();
		for (DeptEmp de : delist) {
			res.add(new ActiveDeptManagerResponse(de.getDeptEmpCompositeKey().getEmpNo().getFirstName(),
					de.getDeptEmpCompositeKey().getEmpNo().getLastName(),
					de.getDeptEmpCompositeKey().getDeptNo().getDeptName(), de.getFromDate()));
		}

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, res, request.getRequestURI()), HttpStatus.OK);

	}
}
