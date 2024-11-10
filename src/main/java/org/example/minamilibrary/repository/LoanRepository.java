package org.example.minamilibrary.repository;

import org.example.minamilibrary.model.Loan;
import org.example.minamilibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	int countByUserAndReturnDateIsNull(User user);
	List<Loan> findByUserAndReturnDateIsNull(User user);
	List<Loan> findAllByUser(User user);
}
