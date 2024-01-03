package com.github.bproenca.crudbcp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByAge(Integer age, Pageable pageable);
    Page<User> findByGender(String Gender, Pageable pageable);
}