package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sec.project.service.AccountService;

@Controller
public class DefaultController {

	@Autowired
	private AccountService accountService;

	@RequestMapping("/")
	public String defaultMapping(Model model) {
		if (accountService.isLoggedIn()) {
			model.addAttribute("account", accountService.getAutenticatedUser());
		}
		return "index";
	}

}
