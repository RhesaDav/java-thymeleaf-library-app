package org.example.minamilibrary.repository;

import org.example.minamilibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
	int countByAvailableTrue();
}
