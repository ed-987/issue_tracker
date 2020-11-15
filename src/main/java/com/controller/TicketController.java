package com.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Activity;
import com.model.Ticket;
import com.service.ActivityService;
import com.service.TicketService;

@Controller
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ActivityService activityService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
  
    @GetMapping(path="/tickets")
    public String ticketsPage(@RequestParam(required = false) String sort ,@RequestParam(required = false) String filter, Model model, @AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
      model.addAttribute("admin",false);
      model.addAttribute("tickets", ticketService.findAllTickets(filter, sort));
      model.addAttribute("sort", sort);
      model.addAttribute("filter", filter);
  	  if(principal != null) {
		String user = principal.getAttribute("email");
		model.addAttribute("user",user);
        String roles = principal.getAttribute("http://role/").toString();
      	@SuppressWarnings("unchecked")
 		List<String> result = new ObjectMapper().readValue(roles, List.class);
     	if(result.contains("ADMIN")) {
     		model.addAttribute("admin",true);
     	}
  	  }
      return "tickets";
    }
    
    @GetMapping(path="/ticket/open/{id}")
    public String openTicketPage(@PathVariable(required = false) Integer id ,HttpServletRequest request, Model model, @AuthenticationPrincipal OAuth2User principal) {
    	model.addAttribute("activity",new Activity());
        model.addAttribute("statusOptions", TicketService.getStatusoptions());
        if(principal != null) {
    		String user = principal.getAttribute("email");
    		model.addAttribute("user",user);
    	}
        Ticket ticket=ticketService.getTicket(id);
		model.addAttribute("ticket", ticket);
    	model.addAttribute("activityHistory", activityService.getActivityHistory(ticket.getId()));
        return "open_ticket";
    }
    
    @GetMapping(path="/ticket/new")
    public String newTicketPage(Model model, @AuthenticationPrincipal OAuth2User user, HttpServletRequest request) {
      Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
      logger.debug(user.getAttribute("email"));
      model.addAttribute("statusOptions", TicketService.getStatusoptions());
      if (inputFlashMap != null) {
      	if(inputFlashMap.get("success").equals("saved")) {
              model.addAttribute("ticket", inputFlashMap.get("ticket"));
      	} 
      } else {
    	Ticket ticket = new Ticket();
    	ticket.setUser(user.getAttribute("email"));
      	model.addAttribute("ticket", ticket);
      }      
      return "new_ticket";
    }
    
    @PostMapping(path="/ticket/save")
    public String saveTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir) {
      ticketService.saveTicket(ticket);
      redir.addFlashAttribute("success", "saved");
      redir.addFlashAttribute("ticket", ticket);
      logger.debug("outp:{}",ticket.toString());
      return "redirect:/ticket/new";
    }

    @PostMapping(path="/ticket/update")
    public String updateTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir) {
      logger.debug("outp-ticket_update: {}",ticket.toString());
      ticketService.updateTicket(ticket);
      redir.addFlashAttribute("success", "updated");
      return "redirect:/ticket/open/"+ticket.getId().toString();
    }
    
    @PostMapping(path="/ticket/delete")
    //@ResponseBody
    public String deleteTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir, Model model) {
      //redir.addFlashAttribute("ticket", ticketService.getTicket(ticket.getId()));
      Ticket t=ticketService.getTicket(ticket.getId());
      t.setStatus("Closed");
      ticketService.saveTicket(t);
      redir.addFlashAttribute("success", "closed");
      return "redirect:/ticket/open/"+ticket.getId().toString();
    }
    
}