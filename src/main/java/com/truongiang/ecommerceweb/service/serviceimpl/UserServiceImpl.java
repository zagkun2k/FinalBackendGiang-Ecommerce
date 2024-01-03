package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.User;
import com.truongiang.ecommerceweb.repository.UserRepository;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkExistedUserByEmail(String email) {

        return this.userRepository.existsByEmail(email);

    }

    @Override
    public Optional<User> getUserByEmail(String email) {

        return this.userRepository.findByEmail(email);

    }

    @Override
    public User findUserByToken(String token) {

        return this.userRepository.findByToken(token);

    }

    @Override
    public void saveUser(User user) {

        this.userRepository.save(user);

    }

    @Override
    public User save(User user) {

        this.userRepository.save(user);
        return user;

    }

    @Override
    public boolean checkExistedUserById(Long id) {

        return this.userRepository.existsById(id);

    }

    @Override
    public List<User> findUsersByStatusTrue() {

        return this.userRepository.findByStatusTrue();

    }

    @Override
    public Optional<User> findUserById(Long id) {

        return this.userRepository.findById(id);

    }

}
