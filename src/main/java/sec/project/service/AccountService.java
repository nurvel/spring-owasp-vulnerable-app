package sec.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	public boolean isLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
//        return (SecurityContextHolder.getContext().getAuthentication() != null &&
//                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
//                !(SecurityContextHolder.getContext().getAuthentication() 
//                    instanceof AnonymousAuthenticationToken));
	}

	public Account getAutenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return accountRepository.findByName(auth.getName());
	}

}
