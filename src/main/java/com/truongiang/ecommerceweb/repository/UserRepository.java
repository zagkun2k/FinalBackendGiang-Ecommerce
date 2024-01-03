package com.truongiang.ecommerceweb.repository;

import com.truongiang.ecommerceweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByStatusTrue();

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String username);

	User findByToken(String token);

}
