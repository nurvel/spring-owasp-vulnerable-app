package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sec.project.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
