package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sec.project.domain.Course;

public interface GradeRepository extends JpaRepository<Grade, Long> {

}
