package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.Ticket;
import com.service.TicketService;

@Controller
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
  
    @GetMapping(path="/tickets")
    public String ticketsPage(@RequestParam(required = false) String sort ,Model model) {

      model.addAttribute("tickets", ticketService.findAllTickets(sort));

      logger.debug("output: {}",sort);
      return "tickets";
    }
    
    @GetMapping(path="/tickets/new")
    public String newTicketPage(Model model) {

      model.addAttribute("ticket", new Ticket());

      return "new_ticket";
    }
    
    @PostMapping(path="/tickets/save")
    @ResponseBody
    public String saveTicket(@ModelAttribute Ticket ticket) {

      ticketService.saveTicket(ticket);

      return "ticket saved";
    }

}