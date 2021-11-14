package com.surface1989.surface1989store.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.surface1989.surface1989store.entity.User;
import com.surface1989.surface1989store.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public void UserRegister(User user) {
		userRepository.save(user);
	}
	public Page<User> getAllUsers(int page) {
		Pageable pageable = PageRequest.of(page, 4);
		Page<User> users = userRepository.findAll(pageable);
		return users;
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
