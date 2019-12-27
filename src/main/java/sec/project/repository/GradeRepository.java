package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sec.project.domain.Course;
import sec.project.domain.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

}
