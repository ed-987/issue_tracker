package com.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.repository.TicketRepository;

@Service
public class TicketService {

	private static final String[] statusOptions = {"New", "Pending", "Completed"};
	
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

	public void updateTicket(Ticket t) {
	    Ticket ticket = ticketRepository.findById(t.getId()).get();
	    ticket.setStatus(t.getStatus()); 
        ticket.setDescription(t.getDescription()); 
        ticketRepository.save(ticket);		
	}

	public void deleteTicket(Integer id) {
		ticketRepository.deleteById(id);		
	}

	public static String[] getStatusoptions() {
		return statusOptions;
	}

	public HashMap<String, String>  getDashboard(String user) {
	    HashMap<String, String> dashboard = new HashMap<String, String>();
		dashboard.put("allTickets", String.valueOf(ticketRepository.findAll().size()));
		dashboard.put("myTickets", String.valueOf(ticketRepository.findByUser(user).size()));
		dashboard.put("myNewTickets", String.valueOf(ticketRepository.findByUserAndStatus(user,"New").size()));
		return dashboard;
	}

	public void deleteTickets(List<Ticket> tickets) {
        for (int i = 0; i <= tickets.size()-1; i++) {
        	if(tickets.get(i).getFlag()) {
              deleteTicket(tickets.get(i).getId());
        	}
        }
		
	}


}
