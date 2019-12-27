package sec.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sec.project.domain.Account;
import sec.project.repository.CourseRepository;
import sec.project.service.AccountService;

@Controller
public class TeachersLoungeController {

	@Autowired
	AccountService accountService;

	@Autowired
	CourseRepository courseRepository;

	@GetMapping("/lounge")
	public String lounge(Model model) {

		Account account = accountService.getAutenticatedUser();
		model.addAttribute("account", account);
		model.addAttribute("courses", courseRepository.findAll());

		return "lounge";

	}



}
