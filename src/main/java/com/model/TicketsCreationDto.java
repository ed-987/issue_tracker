package com.model;

import java.util.ArrayList;
import java.util.List;

public class TicketsCreationDto {
	
    private List<Ticket> tickets =new ArrayList<Ticket>();
    
    // default and parameterized constructor
    
 
    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

	public TicketsCreationDto() {
	}

	public TicketsCreationDto(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
    
}
