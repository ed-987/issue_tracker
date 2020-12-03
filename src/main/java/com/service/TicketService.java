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
//	public static Integer page = 0;
//	public static Boolean hasNext;
//	public static Boolean hasPrevious;
	public static String sort = "id";
	public static Integer pageSize = 25;
	//public static Boolean sortAscending = true;
	
	@Autowired
	TicketRepository ticketRepository;
	
	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);	
	}

	public Slice<Ticket> findAllTickets(String filter, String sort, String status, Boolean sortAscending, Integer page) {
		if(filter == null) {filter="";}else {
			filter = filter.toLowerCase();
			filter = transformFilter(filter);
		}
		Integer id;
		try {
		    id=Integer.parseInt(filter);
		} catch (NumberFormatException e) {
			id=0;
		}
		if(sort == null) {sort="id";}
//		logger.debug("filter:{}",filter);
		Pageable pageable;
		if(sortAscending) {
		  pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC,sort,"id"));
		} else {
		  pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,sort,"id"));
		}
//		Slice<Ticket> slice = ticketRepository.findByAndSort(id, filter, status, pageable);
//		hasNext=slice.hasNext();
//		hasPrevious=slice.hasPrevious();
//		List<Ticket> tickets = slice.getContent();
		//List<Ticket> tickets=ticketRepository.findByAndSort(id, filter, pageable);
		//List<Ticket> tickets=ticketRepository.findByAndSort(id, filter, Sort.by(Sort.Direction.ASC,sort),firstPageWithTwoElements);
//		for(Ticket ticket: tickets) {
//			ticket.create_display();
//		}
		return ticketRepository.findByAndSort(id, filter, status, pageable);
	}
	
	public Ticket getTicket(Integer id) {
		Ticket ticket = ticketRepository.findById(id).get();
		ticket.create_display();
		return ticket;
	}

	public void updateTicket(Ticket t) {
	    Ticket ticket = ticketRepository.findById(t.getId()).get();
	    ticket.setStatus(t.getStatus()); 
        ticket.setDescription(t.getDescription()); 
        ticket.setUser(t.getUser()); 
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
