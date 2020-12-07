package com.service;	

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.model.User;
import com.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public List<User> getUser(String name) {
		
		return userRepository.findByName(name);
		
	}
	
	public void saveUser(User user) {
		userRepository.save(user);	
	}

	public void updateUserDarkMode(String name, Boolean dark_mode) {
	    User user = getUser(name).get(0);
	    user.setDark_mode(dark_mode); 
        userRepository.save(user);		
	}

	public void updateUserColumns(String name, HashMap<String, Boolean> columns) {
	    User user = getUser(name).get(0);
	    user.setColumns(columns);
        userRepository.save(user);		
	}
	public void updateUserEmail(String name, String email) {
	    User user = getUser(name).get(0);
	    user.setEmail(email);
        userRepository.save(user);		
	}
	
	public List<String> getUsers(){
		List<User> users_list=userRepository.findAll(Sort.by(Sort.Direction.ASC, "email"));
		List<String> users = new ArrayList<String>();
		for(User user : users_list) {
			users.add(user.getEmail());
		}
		return users;
	}

}
