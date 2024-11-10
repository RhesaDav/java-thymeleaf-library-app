package org.example.minamilibrary.repository;

import org.example.minamilibrary.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	List<Loan> findByUserId(Long userId);
	List<Loan> findByReturnDateIsNull();
}
