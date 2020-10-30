package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Activity;
import com.model.Ticket;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	
	List<Activity> findByTicketIdOrderByIdDesc(Integer ticketId);

}