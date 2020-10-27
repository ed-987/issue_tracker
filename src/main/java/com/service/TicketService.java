package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	TicketRepository ticketRepository;
	
	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);
		
	}

	public List<Ticket> findAllTickets(String sort) {
		if(sort==null) {
	      return ticketRepository.findAll();
		}else {
		  return ticketRepository.findAll(Sort.by(Sort.Direction.ASC,sort));
		}
	}
	
	public Ticket getTicket(Integer id) {
		return ticketRepository.findById(id).get();
	}

}
