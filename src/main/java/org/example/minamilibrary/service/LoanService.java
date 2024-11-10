package org.example.minamilibrary.service;

import org.example.minamilibrary.model.Book;
import org.example.minamilibrary.model.Loan;
import org.example.minamilibrary.model.User;
import org.example.minamilibrary.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {
	private final LoanRepository loanRepository;
	
	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}
	
	public List<Loan> getAllLoans() {
		return loanRepository.findAll();
	}
	
	public List<Loan> getActiveLoans() {
		return loanRepository.findAll();
	}
	
	public Loan getLoanById(Long id) {
		return loanRepository.findById(id).orElse(null);
	}
	
	public Loan borrowBook(User user, Book book) {
		Loan loan = new Loan();
		loan.setUser(user);
		loan.setBook(book);
		loan.setBorrowDate(LocalDate.now());
		loan.setDueDate(LocalDate.now().plusDays(2));
		book.setAvailable(false);
		return loanRepository.save(loan);
	}
	
	public Loan returnBook(Loan loan) {
		LocalDate returnDate = LocalDate.now();
		loan.setReturnDate(returnDate);
		
		if (returnDate.isAfter(loan.getDueDate())) {
			Long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), returnDate);
			loan.setFine(daysLate * 1000);
		}
		
		loan.getBook().setAvailable(true);
		return loanRepository.save(loan);
	}
	
	public int countActiveLoansByUser(User user) {
		return loanRepository.countByUserAndReturnDateIsNull(user);
	}
	
	public double calculateOutstandingFines(User user) {
		List<Loan> loans = loanRepository.findByUserAndReturnDateIsNull(user);
		return loans.stream().mapToDouble(loan -> loan.getFine()).sum();
	}
	
	public List<Loan> findLoansByUser(User user) {
		return loanRepository.findAllByUser(user);
	}
	
	public List<Loan> findActiveLoansByUser(User user) {
		return loanRepository.findByUserAndReturnDateIsNull(user);
	}
}
