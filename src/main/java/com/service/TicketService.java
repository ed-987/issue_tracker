package com.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.model.Ticket;
import com.repository.TicketRepository;

@Service
public class TicketService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String[] statusOptions = {"New", "In progress", "Pending", "Completed", "Closed"};
	public static final String sortDefault ="id";
	public static final String sortascendingDefault = "false";
	public static String sort = sortDefault;
	public static Integer pageSize = 25;
	
	@Autowired
	TicketRepository ticketRepository;
	
	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);	
	}

	public Slice<Ticket> findAllTickets(String filter, String sort, String status, String user, Boolean sortAscending, Integer page) {
		filter = filter.toLowerCase();
		String filter_id = transformFilter(filter);

		Integer id;
		try {
		    id=Integer.parseInt(filter_id);
		} catch (NumberFormatException e) {
			id=0;
		}

		Pageable pageable;
		if(sortAscending) {
		  pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC,sort,"id"));
		} else {
		  pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,sort,"id"));
		}
		return ticketRepository.findByAndSort(id, filter, status, user, pageable);
	}
	
	public Ticket getTicket(Integer id) {
		Ticket ticket = ticketRepository.findById(id).get();
		//ticket.create_display();
		return ticket;
	}

	public void updateTicket(Ticket t) {
	    Ticket ticket = ticketRepository.findById(t.getId()).get();
	    ticket.setTitle(t.getTitle()); 
	    ticket.setStatus(t.getStatus()); 
        ticket.setDescription(t.getDescription()); 
        ticket.setUser(t.getUser()); 
        ticket.setUpdated(t.getUpdated()); 
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
	
	private String transformFilter(String filter) {
		filter = filter.replace("inc00000", "");
		filter = filter.replace("inc0000", "");
		filter = filter.replace("inc000", "");
		filter = filter.replace("inc00", "");
		filter = filter.replace("inc0", "");
		filter = filter.replace("inc", "");
		return filter;
	}


}
