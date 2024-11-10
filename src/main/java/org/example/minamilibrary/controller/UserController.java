package org.example.minamilibrary.controller;


import org.springframework.security.core.Authentication;
import org.example.minamilibrary.model.Loan;
import org.example.minamilibrary.model.User;
import org.example.minamilibrary.service.BookService;
import org.example.minamilibrary.service.LoanService;
import org.example.minamilibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final LoanService loanService;
	private final BookService bookService;
	
	public UserController(UserService userService, LoanService loanService, BookService bookService) {
		this.userService = userService;
		this.loanService = loanService;
		this.bookService = bookService;
	}
	
	@GetMapping("/dashboard")
	public String userDashboard(Model model, Authentication auth) {
		User user = userService.findByUsername(auth.getName());
		int borrowedBooks = loanService.countActiveLoansByUser(user);
		double outstandingFines = loanService.calculateOutstandingFines(user);
		int availableBooks = bookService.countAvailableBooks();
		List<Loan> userActiveLoans = loanService.findActiveLoansByUser(user);
		List<Loan> userLoans = loanService.findLoansByUser(user);
		
		model.addAttribute("borrowedBooks", borrowedBooks);
		model.addAttribute("outstandingFines", outstandingFines);
		model.addAttribute("availableBooks", availableBooks);
		model.addAttribute("userActiveLoans", userActiveLoans);
		model.addAttribute("userLoans", userLoans);
		
		return "user/dashboard";
	}
}
