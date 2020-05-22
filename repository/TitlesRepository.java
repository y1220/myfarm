package it.course.myfarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.course.myfarm.entity.Employee;
import it.course.myfarm.entity.Titles;
import it.course.myfarm.entity.TitlesCompositeKey;

public interface TitlesRepository extends JpaRepository<Titles, TitlesCompositeKey> {

	Optional<Titles> findByTitlesCompositeKeyEmpNoAndToDateIsNull(Employee emp);

	List<Titles> findByTitlesCompositeKeyEmpNo(Employee employee);

}
