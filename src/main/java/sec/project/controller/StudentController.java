package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class StudentController {

	@Autowired
	AccountRepository accountRepository;

	@RequestMapping("/student/{id}")
	public String studentInfo(Model model, @PathVariable Long id) {

		System.out.println("IIDEE " + id);

		Account account = accountRepository.getOne(id);
		model.addAttribute("account", account);

		return "student";
	}

}
