package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
   //Search By Name (partial match, case-insensitive)
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    //Search By Email(partial match, case-insensitive)
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    // Search by both name AND email (partial match, case-insensitive)
    Page<User> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT ('%', :domain))")
    Page<User> findUsersByDomain(@Param("domain") String domain, Pageable pageable );
}
