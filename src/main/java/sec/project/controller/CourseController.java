package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sec.project.repository.CourseRepository;
import sec.project.service.AccountService;

@Controller
public class CourseController {

	@Autowired
	AccountService accountService;

	@Autowired
	CourseRepository courseRepository;

	@GetMapping("/course")
	public String allCourses(Model model) {
		model.addAttribute("account", accountService.getAutenticatedUser());
		model.addAttribute("courses", courseRepository.findAll());
		return "course";
	}

	@PostMapping("/course/feedback/{id}")
	public String courseFeedback(HttpServletRequest request, @PathVariable Long id, @RequestParam String feedback) {

		String role = accountService.getAutenticatedUser().getAuthorities().get(0);

		// FLAW 1: A1:2017-Injection
		String query = "INSERT INTO COURSE_FEED_BACK (course_id, feed_back) VALUES ('" + id + "', '" + role + ": " + feedback + "')";
		System.out.println(query);

		try (Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "")) {
			connection.createStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "redirect:" + request.getHeader("Referer");
	}

}
