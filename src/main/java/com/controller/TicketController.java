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
	private TicketService ticketService;
	
	@Autowired
	private ActivityService activityService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
  
    @GetMapping(path="/tickets")
    public String ticketsPage(@RequestParam(required = false) String sort, @RequestParam(required = false) Boolean sortascending, 
    		@RequestParam(required = false) String filter, @RequestParam(required = false) Integer page, Model model, 
    		@RequestParam(required = false) String status,
    		@AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
      ScreenService.admin_screen_top=true;
      model.addAttribute("admin",false);
      if(status == null) {status="";}
//      if(page != null) {
//    	  TicketService.page=page;
//      }else {
//    	  TicketService.page = 0;
//      }
      if(page == null) {page=0;}
      if(sort == null) {sort="id";}
      if(sortascending == null) {sortascending=true;} 
      if(!sort.equals(TicketService.sort)) {
    	  sortascending=true;
    	  TicketService.sort=sort;
      }
      Slice<Ticket> slice = ticketService.findAllTickets(filter, sort, status, sortascending, page);
      model.addAttribute("tickets", slice.getContent());
      model.addAttribute("sort", sort);
      model.addAttribute("sortascending", sortascending);
      model.addAttribute("filter", filter);
      model.addAttribute("status", status);
      model.addAttribute("page", slice.getNumber());
      model.addAttribute("hasNext", slice.hasNext());
      model.addAttribute("hasPrevious", slice.hasPrevious());
      model.addAttribute("showing", String.valueOf(1+slice.getNumber()*slice.getSize())+"-"+
        String.valueOf(slice.getNumber()*slice.getSize()+slice.getNumberOfElements()));
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
  	  model.addAttribute("dark_mode",ScreenService.dark_mode);
  	  model.addAttribute("scroll_top",ScreenService.tickets_screen_top);
  	  
      logger.debug("slice:{} ",slice.getNumberOfElements());	
  	  
      return "tickets";
    }
    
    @GetMapping(path="/ticket/open/{id}")
    public String openTicketPage(@PathVariable(required = false) Integer id ,HttpServletRequest request, Model model, @AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
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
    	
    	logger.debug("date:{}",ticket.getCreatedDateFormat());
    	
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
      return "new_ticket";
    }
    
    @PostMapping(path="/ticket/save")
    public String saveTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir) {
      ticketService.saveTicket(ticket);
      ticket.create_display();
      redir.addFlashAttribute("success", "ticket saved");
      redir.addFlashAttribute("ticket", ticket);
      logger.debug("outp:{}",ticket.toString());
      return "redirect:/ticket/new";
    }

    @PostMapping(path="/ticket/update")
    public String updateTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir) {
      logger.debug("outp-ticket_update: {}",ticket.toString());
      ticketService.updateTicket(ticket);
      redir.addFlashAttribute("success", "ticket updated");
      return "redirect:/ticket/open/"+ticket.getId().toString();
    }
    
    @PostMapping(path="/ticket/close")
    //@ResponseBody
    public String closeTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir, Model model) {
      //redir.addFlashAttribute("ticket", ticketService.getTicket(ticket.getId()));
      Ticket t=ticketService.getTicket(ticket.getId());
      t.setStatus("Closed");
      ticketService.saveTicket(t);
      redir.addFlashAttribute("success", "ticket closed");
      return "redirect:/ticket/open/"+ticket.getId().toString();
    }
    
}