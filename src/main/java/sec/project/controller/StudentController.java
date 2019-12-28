package sec.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sec.project.domain.Account;
import sec.project.domain.CourseSubmission;
import sec.project.repository.AccountRepository;
import sec.project.repository.CourseSubmissionRepository;
import sec.project.service.AccountService;

@Controller
public class StudentController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	CourseSubmissionRepository courseSubmissionRepository;

	@RequestMapping("/student/{id}")
	public String studentInfo(Model model, @PathVariable Long id) {
		// Account account accountService.getAutenticatedUser(); // FIX: user the
		// authenticated user instead of pathvariable id
		Account account = accountRepository.getOne(id); // FIX: Remove this
		model.addAttribute("account", account);
		return "student";
	}

 
	@PreAuthorize("hasAuthority('TEACHER')")
	@PostMapping("/student/{id}/grade")
	public String lounge(HttpServletRequest request, @RequestParam Long submissionid, @RequestParam Integer grade, @PathVariable Long id) {
		CourseSubmission courseSubmission = courseSubmissionRepository.getOne(submissionid);
		courseSubmission.setGrade(grade);
		courseSubmissionRepository.save(courseSubmission);
		return "redirect:" + request.getHeader("Referer");
		
	}

}
