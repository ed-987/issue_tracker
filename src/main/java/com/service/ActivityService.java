package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.model.Activity;
import com.model.Ticket;
import com.repository.ActivityRepository;
import com.repository.TicketRepository;

@Service
public class ActivityService {
	
	@Autowired
	ActivityRepository activityRepository;
	
	public void saveActivity(Activity activity) {
		activityRepository.save(activity);	
	}
//
//	public List<Ticket> findAllTickets(String sort) {
//		if(sort==null) {
//	      return ticketRepository.findAll();
//		}else {
//		  return ticketRepository.findAll(Sort.by(Sort.Direction.ASC,sort));
//		}
//	}
//	
//	public Ticket getTicket(Integer id) {
//		return ticketRepository.findById(id).get();
//	}
//
//	public void updateTicket(Ticket t) {
//	    Ticket ticket = ticketRepository.findById(t.getId()).get();
//	    ticket.setStatus(t.getStatus()); 
//        ticket.setDescription(t.getDescription()); 
//        ticketRepository.save(ticket);		
//	}
//
//	public void deleteTicket(Integer id) {
//		ticketRepository.deleteById(id);		
//	}
//
//	public static String[] getStatusoptions() {
//		return statusOptions;
//	}

	public List<Activity> getActivityHistory(Integer ticketId) {
		return activityRepository.findByTicketIdOrderByIdDesc(ticketId);
	}


}
