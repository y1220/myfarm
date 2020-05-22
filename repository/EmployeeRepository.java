package it.course.myfarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.course.myfarm.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	Optional<Employee> findByEmpNo(String empNo);

	List<Employee> findByEmpNoIn(List<String> list);

}
