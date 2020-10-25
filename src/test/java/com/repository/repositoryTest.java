package com.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.model.User;
import com.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Transactional
public class repositoryTest {

	@Autowired
	private UserRepository userRepository;
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	    
	@Test
	public void getAllUsers() {
		//userRepository.save(new User("test","test@x.com"));
		logger.info("test results: {}",userRepository.findAll().toString());
		
	}
    
}
