package it.course.myfarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.course.myfarm.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, char[]> {

	Optional<Department> findByDeptNo(char[] deptNo);

	List<Department> findByDeptNoIn(List<char[]> list);

}
