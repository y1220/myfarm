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
import it.course.myfarm.entity.DeptManager;
import it.course.myfarm.entity.DeptManagerCompositeKey;
import it.course.myfarm.entity.Employee;
import it.course.myfarm.payload.request.DeptEmpRequest;
import it.course.myfarm.payload.request.DeptSearchRequest;
import it.course.myfarm.payload.response.ActiveDeptManagerResponse;
import it.course.myfarm.payload.response.ApiResponseCustom;
import it.course.myfarm.repository.DepartmentRepository;
import it.course.myfarm.repository.DeptManagerRepository;
import it.course.myfarm.repository.EmployeeRepository;

@RestController
@RequestMapping("/department-manager")
public class DepartmentManagerController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DeptManagerRepository deptManagerRepository;

	@PostMapping("/insert-department-manager")
	public ResponseEntity<ApiResponseCustom> insertDepartment(@RequestBody DeptEmpRequest deRequest,
			HttpServletRequest request) {

		Optional<Employee> e = employeeRepository.findByEmpNo(deRequest.getEmpNo());
		if (!e.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Employee not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		// ALREADY EMPLOYEE HAS MANAGER ROLE IN ANOTHER DEPARTMENT
		Optional<DeptManager> mError = deptManagerRepository.findByDeptManagerCompositeKeyEmpNoAndToDateIsNull(e.get());
		if (mError.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null,
							"Employee has already the role manager in another department", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		Optional<Department> d = departmentRepository.findByDeptNo(deRequest.getDeptNo());
		if (!d.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(), 404, null, "Department not found", request.getRequestURI()),
					HttpStatus.NOT_FOUND);

		// ALREADY DEPARTMENT OWNS MANAGER WHO DOES ROLE IN ACTIVE
		Optional<DeptManager> dError = deptManagerRepository
				.findByDeptManagerCompositeKeyDeptNoAndToDateIsNull(d.get());
		if (dError.isPresent())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"Department has already the manager", request.getRequestURI()), HttpStatus.NOT_FOUND);

		DeptManagerCompositeKey dmck = new DeptManagerCompositeKey(e.get(), d.get());

		DeptManager dm = new DeptManager();
		dm.setDeptManagerCompositeKey(dmck);
		dm.setFromDate(deRequest.getFromDate());
		deptManagerRepository.save(dm);

		return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 200, null,
				"New department manager data successfully created", request.getRequestURI()), HttpStatus.OK);
	}

	@GetMapping("/all-managers-by-deptNo")
	public ResponseEntity<ApiResponseCustom> allManagersByDeptNo(@RequestBody DeptSearchRequest dmr,
			HttpServletRequest request) {

		List<Department> dlist = departmentRepository.findByDeptNoIn(dmr.getDeptNos());
		if (dlist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"No department is registered", request.getRequestURI()), HttpStatus.NOT_FOUND);

		// TRANSFORM IT INTO READABLE SHAPE
		List<DeptManager> dmlist = deptManagerRepository.findByDeptManagerCompositeKeyDeptNoInAndToDateIsNull(dlist);
		if (dmlist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"No manager is registered for these departments", request.getRequestURI()), HttpStatus.NOT_FOUND);
		List<ActiveDeptManagerResponse> res = new ArrayList<ActiveDeptManagerResponse>();
		for (DeptManager dm : dmlist) {
			res.add(new ActiveDeptManagerResponse(dm.getDeptManagerCompositeKey().getEmpNo().getFirstName(),
					dm.getDeptManagerCompositeKey().getEmpNo().getLastName(),
					dm.getDeptManagerCompositeKey().getDeptNo().getDeptName(), dm.getFromDate()));
		}

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, res, request.getRequestURI()), HttpStatus.OK);

	}

}
