package com.truongiang.ecommerceweb.common.configuration.security;

import com.truongiang.ecommerceweb.model.User;
import com.truongiang.ecommerceweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found with username" + username));

		return UserDetailsImpl.build(user);

	}

}