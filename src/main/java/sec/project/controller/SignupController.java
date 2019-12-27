package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;
import sec.project.service.AccountService;

@Controller
public class SignupController {

	@Autowired
	private SignupRepository signupRepository;

	@Autowired
	private AccountService accountService;

	@RequestMapping("/")
	public String defaultMapping(Model model) {

		if (accountService.isLoggedIn()) {
			model.addAttribute("account", accountService.getAutenticatedUser());
			System.out.println("logged in");
			//System.out.println(accountService.getAutenticatedUser().getName());
		}

		return "index";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String loadForm() {
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String submitForm(@RequestParam String name, @RequestParam String address) {
		signupRepository.save(new Signup(name, address));
		return "done";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String testForm(@RequestParam String test) {

		String add = "testijuttu";
		String query = "INSERT INTO signup (address, name) VALUES ('" + add + "', '" + test + "')";

		System.out.println(query);

//		Connection connection;
		try (Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "")) {
			connection.createStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "done";
	}

}
