package it.course.myfarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.course.myfarm.entity.Department;
import it.course.myfarm.entity.DeptManager;
import it.course.myfarm.entity.DeptManagerCompositeKey;
import it.course.myfarm.entity.Employee;

public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManagerCompositeKey> {

	// List<DeptManager> findByDeptManagerCompositeKey(DeptManagerCompositeKey
	// deck);
	List<DeptManager> findByDeptManagerCompositeKeyEmpNo(Employee empNo);

	List<DeptManager> findByDeptManagerCompositeKeyDeptNo(Department deptNo);

	Optional<DeptManager> findByDeptManagerCompositeKeyEmpNoAndToDateIsNull(Employee employee);

	List<DeptManager> findByDeptManagerCompositeKeyDeptNoIn(List<Department> dlist);

	List<DeptManager> findByDeptManagerCompositeKeyDeptNoInAndToDateIsNull(List<Department> dlist);

	Optional<DeptManager> findByDeptManagerCompositeKeyDeptNoAndToDateIsNull(Department department);

}
