package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Activity;
import com.model.Ticket;
import com.model.TicketsCreationDto;
import com.security.ForbiddenException;
import com.security.SecurityTools;
import com.service.ActivityService;
import com.service.TicketService;

@Controller
public class AdminController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ActivityService activityService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @GetMapping("/admin")
//    @ResponseBody
    public String admin(@AuthenticationPrincipal OAuth2User principal,HttpServletResponse response, @RequestParam(required = false) String sort ,Model model) throws IOException{
    	SecurityTools.adminAuth(principal.getAttribute("http://role/").toString());   
		String user = principal.getAttribute("email");
		model.addAttribute("user",user);
    	TicketsCreationDto ticketsForm = new TicketsCreationDto();
    	List<Ticket> tickets=ticketService.findAllTickets(null, sort);
    	//tickets.add(new Ticket());
        for (int i = 0; i <= tickets.size()-1; i++) {
            ticketsForm.addTicket(tickets.get(i));
        }
	    model.addAttribute("form", ticketsForm);
        //model.addAttribute("tickets", ticketService.findAllTickets(sort));
    	return "admin";
        
    }
    
    @PostMapping("/admin/delete")
    //@ResponseBody
    public String delete(@ModelAttribute TicketsCreationDto form, RedirectAttributes redir, @AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
    	SecurityTools.adminAuth(principal.getAttribute("http://role/").toString());   
        logger.debug("outp:{}", form.getTickets().toString());
        //model.addAttribute("tickets", ticketService.findAllTickets(sort));
        ticketService.deleteTickets(form.getTickets());
        redir.addFlashAttribute("success", "deleted");
        return "redirect:/admin";
        
    }
	
	
}