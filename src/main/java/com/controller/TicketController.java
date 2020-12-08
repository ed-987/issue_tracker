package com.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
import com.service.ScreenService;
import com.service.TicketService;

@Controller
public class TicketController {
	
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ActivityService activityService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
  
    @GetMapping(path="/tickets")
    public String ticketsPage(
    		Model model,
    		@RequestParam(required = false, defaultValue = TicketService.sortDefault) String sort, 
    		@RequestParam(required = false, defaultValue = TicketService.sortascendingDefault) Boolean sortascending, 
    		@RequestParam(required = false, defaultValue = "") String filter, 
    		@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "") String status, 
    		@RequestParam(required = false, defaultValue = "") String user_sort, 
    		@RequestParam(required = false) Integer pagesize,
    		@AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
      ScreenService.admin_screen_top=true;
      
      if(pagesize == null) {pagesize=TicketService.pageSize;}else {
    	  TicketService.pageSize=pagesize;
      }
      if(!sort.equals(TicketService.sort)) {
    	  sortascending=Boolean.parseBoolean(TicketService.sortascendingDefault);;
    	  TicketService.sort=sort;
      }
      Slice<Ticket> slice = ticketService.findAllTickets(filter, sort, status, user_sort, sortascending, page);
      model.addAttribute("tickets", slice.getContent());
      model.addAttribute("sort", sort);
      model.addAttribute("sortascending", sortascending);
      model.addAttribute("filter", filter);
      model.addAttribute("status", status);
      model.addAttribute("user_sort", user_sort);
      model.addAttribute("pagesize", pagesize);
      model.addAttribute("page", slice.getNumber());
      model.addAttribute("hasNext", slice.hasNext());
      model.addAttribute("hasPrevious", slice.hasPrevious());
      String showing = (slice.getNumberOfElements()==0) ? " " : String.valueOf(1+slice.getNumber()*slice.getSize())+"-"+
    	        String.valueOf(slice.getNumber()*slice.getSize()+slice.getNumberOfElements());
      model.addAttribute("showing", showing);
      
      if(screenService.check_login(principal)) {
		model.addAttribute("user",ScreenService.user_email);
    	model.addAttribute("admin",ScreenService.user_admin);
      }
  	  model.addAttribute("dark_mode",ScreenService.dark_mode);
  	  model.addAttribute("scroll_top",ScreenService.tickets_screen_top);
  	  model.addAttribute("columns",ScreenService.columns);
  	  model.addAttribute("users",ScreenService.users);
      //logger.debug("users:{} ",principal);	
  	  
      return "tickets";
    }
    
    @GetMapping(path="/ticket/open/{id}")
    public String openTicketPage(@PathVariable(required = false) Integer id ,HttpServletRequest request, Model model, 
    		@AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
    	ScreenService.tickets_screen_top=false;
    	model.addAttribute("activity",new Activity());
        model.addAttribute("statusOptions", TicketService.getStatusoptions());
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
        Ticket ticket=ticketService.getTicket(id);
		model.addAttribute("ticket", ticket);
    	model.addAttribute("activityHistory", activityService.getActivityHistory(ticket.getId()));
    	model.addAttribute("dark_mode",ScreenService.dark_mode); 
    	model.addAttribute("users",ScreenService.users);
        return "open_ticket";
    }
    
    @GetMapping(path="/ticket/new")
    public String newTicketPage(Model model, @AuthenticationPrincipal OAuth2User principal, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
      Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
      model.addAttribute("statusOptions", TicketService.getStatusoptions());
      if (inputFlashMap != null) {
      	if(inputFlashMap.get("success").equals("saved")) {
              model.addAttribute("ticket", inputFlashMap.get("ticket"));
      	} 
      } else {
    	Ticket ticket = new Ticket();
    	ticket.setUser(principal.getAttribute("email"));
      	model.addAttribute("ticket", ticket);
      	logger.debug("ticket:{}",ticket.toString());
      }
      if(principal != null) {
        String roles = principal.getAttribute("http://role/").toString();
        @SuppressWarnings("unchecked")
	    List<String> result = new ObjectMapper().readValue(roles, List.class);
        if(result.contains("ADMIN")) {
   		  model.addAttribute("admin",true);
   	    }
      }
  	  model.addAttribute("dark_mode",ScreenService.dark_mode);
  	  model.addAttribute("users",ScreenService.users);
      return "new_ticket";
    }
    
    @PostMapping(path="/ticket/save")
    public String saveTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir) {
      ticketService.saveTicket(ticket);
      redir.addFlashAttribute("success", "Ticket saved");
      redir.addFlashAttribute("ticket", ticket);
      logger.debug("outp:{}",ticket.toString());
      return "redirect:/ticket/new";
    }

    @PostMapping(path="/ticket/update")
    public String updateTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir) {
      //logger.debug("outp-ticket_update: {}",ticket.toString());
      ticket.setUpdated(new Date());
      ticketService.updateTicket(ticket);
      redir.addFlashAttribute("success", "Ticket updated");
      return "redirect:/ticket/open/"+ticket.getId().toString();
    }
    
    @PostMapping(path="/ticket/close")
    //@ResponseBody
    public String closeTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir, Model model) {
      //redir.addFlashAttribute("ticket", ticketService.getTicket(ticket.getId()));
      Ticket t=ticketService.getTicket(ticket.getId());
      t.setStatus("Closed");
      ticketService.saveTicket(t);
      redir.addFlashAttribute("success", "Ticket closed");
      return "redirect:/ticket/open/"+ticket.getId().toString();
    }
    
}