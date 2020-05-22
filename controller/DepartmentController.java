package it.course.myfarm.controller;

import java.time.Instant;
import java.util.List;

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
import it.course.myfarm.payload.response.ApiResponseCustom;
import it.course.myfarm.repository.DepartmentRepository;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	DepartmentRepository departmentRepository;

	@PostMapping("/insert-department")
	public ResponseEntity<ApiResponseCustom> insertDepartment(@RequestBody Department department,
			HttpServletRequest request) {

		Department d = new Department();

		d.setDeptNo(department.getDeptNo());
		d.setDeptName(department.getDeptName());
		departmentRepository.save(d);

		return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 200, null,
				"New department data successfully created", request.getRequestURI()), HttpStatus.OK);
	}

	@GetMapping("/all-departments")
	public ResponseEntity<ApiResponseCustom> allDepartments(HttpServletRequest request) {

		List<Department> dlist = departmentRepository.findAll();
		if (dlist.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(new ApiResponseCustom(Instant.now(), 404, null,
					"No department is registered", request.getRequestURI()), HttpStatus.NOT_FOUND);

		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(), 200, null, dlist, request.getRequestURI()), HttpStatus.OK);

	}

}
