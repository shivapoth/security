package com.ojas.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ojas.model.User;
import com.ojas.repo.UserRepository;

@Service
public class CustomisedUserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = repository.findByUname(username);

		CustomUserDetails userDetails = null;

		if (user != null) {

			userDetails = new CustomUserDetails();

			userDetails.setUser(user);

		} else {

			throw new UsernameNotFoundException("User Not Exist With Name :" + username);
		}
		return userDetails;

	}

}
