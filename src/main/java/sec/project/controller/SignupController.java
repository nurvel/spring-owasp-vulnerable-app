package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

	@Autowired
	private SignupRepository signupRepository;

	@RequestMapping("/")
	public String defaultMapping() {
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
