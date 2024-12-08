package com.perfume.store.repository;

import com.perfume.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<?> searchByEmail(String email);
//    User save(User user);
    boolean existsByEmail(String email);
}
