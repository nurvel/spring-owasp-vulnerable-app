package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sec.project.domain.Account;
import sec.project.repository.AccountRepository;
import sec.project.service.AccountService;

@Controller
public class StudentController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	@RequestMapping("/student/{id}")
	public String studentInfo(Model model, @PathVariable Long id) {
		// Account account accountService.getAutenticatedUser(); // FIX: user the
		// authenticated user instead of pathvariable id
		Account account = accountRepository.getOne(id); // FIX: Remove this
		model.addAttribute("account", account);
		return "student";
	}

}
