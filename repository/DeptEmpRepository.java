package it.course.myfarm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.course.myfarm.entity.Department;
import it.course.myfarm.entity.DeptEmp;
import it.course.myfarm.entity.DeptEmpCompositeKey;
import it.course.myfarm.entity.Employee;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpCompositeKey> {

	// List<DeptEmp> findByDeptEmpCompositeKey(DeptEmpCompositeKey dmck);
	List<DeptEmp> findByDeptEmpCompositeKeyEmpNo(Employee e);

	List<DeptEmp> findByDeptEmpCompositeKeyDeptNo(Department d);

	List<DeptEmp> findByDeptEmpCompositeKeyDeptNoInAndToDateIsNull(List<Department> dlist);

}
