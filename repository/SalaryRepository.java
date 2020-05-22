package it.course.myfarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.course.myfarm.entity.Employee;
import it.course.myfarm.entity.Salary;
import it.course.myfarm.entity.SalaryCompositeKey;

public interface SalaryRepository extends JpaRepository<Salary, SalaryCompositeKey> {

	Optional<Salary> findBySalaryCompositeKeyEmpNoAndToDateIsNull(Employee employee);

	List<Salary> findBySalaryCompositeKeyEmpNo(Employee e);

}
