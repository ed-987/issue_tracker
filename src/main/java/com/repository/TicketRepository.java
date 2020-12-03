package com.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Activity;
import com.model.Ticket;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	List<Ticket> findByUser(String user);
	List<Ticket> findByUserAndStatus(String user, String status);
	
	@Query("SELECT t FROM Ticket t WHERE (t.id = ?1 OR LOWER(t.title) LIKE %?2%) AND t.status LIKE %?3%")
	Slice<Ticket> findByAndSort(Integer id, String title, String status, Pageable pageable);
	//List<Ticket> findByAndSort(Integer id, String title, Sort sort);


}