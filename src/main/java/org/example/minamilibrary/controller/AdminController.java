package org.example.minamilibrary.controller;

import org.example.minamilibrary.model.Book;
import org.example.minamilibrary.model.Loan;
import org.example.minamilibrary.model.User;
import org.example.minamilibrary.service.BookService;
import org.example.minamilibrary.service.LoanService;
import org.example.minamilibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final UserService userService;
	private final BookService bookService;
	private final LoanService loanService;
	
	public AdminController(UserService userService, BookService bookService, LoanService loanService) {
		this.userService = userService;
		this.bookService = bookService;
		this.loanService = loanService;
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("totalUsers", userService.findAll().size());
		model.addAttribute("totalBooks", bookService.getAllBooks().size());
		model.addAttribute("activeLoans", loanService.getActiveLoans().size());
		return "admin/dashboard";
	}
	
	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}
	
	@GetMapping("/users/add")
	public String addUserForm(Model model) {
		model.addAttribute("user", new User());
		return "admin/add-user";
	}
	
	@PostMapping("/users/add")
	public String addUser(@ModelAttribute("user") User user) {
		userService.createUser(user);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/users/{id}/edit")
	public String editUserForm(@PathVariable Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "admin/edit-user";
	}
	
	@PostMapping("/users/{id}/edit")
	public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
		userService.updateUser(id, user);
		return "redirect:/admin/dashboard";
	}
	
	@PostMapping("/users/{id}/delete")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/books")
	public String books(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "admin/books";
	}
	
	@GetMapping("/books/add")
	public String addBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "admin/add-book";
	}
	
	@PostMapping("/books/add")
	public String addBook(@ModelAttribute("book") Book book) {
		bookService.createBook(book);
		return "redirect:/admin/books";
	}
	
	@GetMapping("/loans")
	public String viewLoans(Model model) {
		model.addAttribute("loans", loanService.getAllLoans());
		return "admin/loans";
	}
	
	@GetMapping("/loans/borrow")
	public String showBorrowForm(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("loan", new Loan());
		return "admin/borrow-book";
	}
	
	@PostMapping("/loans/borrow")
	public String borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
		User user = userService.findById(userId);
		Book book = bookService.findById(bookId);
		loanService.borrowBook(user, book);
		return "redirect:/admin/loans";
	}
	
	@PostMapping("/loans/{id}/return")
	public String returnBook(@PathVariable Long id) {
		Loan loan = loanService.getLoanById(id);
		loanService.returnBook(loan);
		return "redirect:/admin/loans";
	}
}
