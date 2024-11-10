package org.example.minamilibrary.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "loan")
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Book book;
	
	private LocalDate borrowDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private double fine;
}
