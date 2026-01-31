package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   //Search By Name (partial match, case-insensitive)
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    //Search By Email(partial match, case-insensitive)
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    // Search by both name AND email (partial match, case-insensitive)
    Page<User> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String name, String email, Pageable pageable);
}
