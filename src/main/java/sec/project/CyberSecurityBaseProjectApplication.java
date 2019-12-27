package sec.project;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import sec.project.domain.Account;
import sec.project.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(CyberSecurityBaseProjectApplication.class);
	}

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	public void initAfterStartup() {

		String password = "salasana";
		List<String> studentAuths = Arrays.asList(new String[] { "STUDENT" });
		List<String> teacherAuths = Arrays.asList(new String[] { "TEACHER" });

		Account student1 = new Account("user", passwordEncoder.encode(password), studentAuths);
		accountRepository.save(student1);

		Account teacher1 = new Account("admin", passwordEncoder.encode(password), teacherAuths);
		accountRepository.save(teacher1);

	}

}
