package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean checkExistedUserByEmail(String email);

    Optional<User> getUserByEmail(String email);

    User findUserByToken(String token);

    void saveUser(User user);
    User save(User user);

    boolean checkExistedUserById(Long id);

    List<User> findUsersByStatusTrue();

    Optional<User> findUserById(Long id);

}
