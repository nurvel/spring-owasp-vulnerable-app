package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sec.project.domain.Course;
import sec.project.domain.CourseSubmission;

public interface CourseSubmissionRepository extends JpaRepository<CourseSubmission, Long> {

}
