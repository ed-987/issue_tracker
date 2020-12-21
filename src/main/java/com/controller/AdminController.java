package com.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Activity;
import com.model.Ticket;
import com.model.TicketsCreationDto;
import com.security.SecurityTools;
import com.service.ActivityService;
import com.service.ScreenService;
import com.service.TicketService;

@Controller
public class AdminController {

	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ActivityService activityService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @GetMapping("/admin")
//    @ResponseBody
    public String admin(Model model, 
    		@RequestParam(required = false, defaultValue = TicketService.sortDefault) String sort, 
    		@RequestParam(required = false, defaultValue = TicketService.sortascendingDefault) Boolean sortascending, 
    		@RequestParam(required = false, defaultValue = "") String filter, 
    		@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "") String status, 
    		@RequestParam(required = false) Integer pagesize,
    		@AuthenticationPrincipal OAuth2User principal,HttpServletResponse response
    		) throws IOException{
    	ScreenService.tickets_screen_top=true;
    	SecurityTools.adminAuth(principal.getAttribute("http://role/").toString());   
		
	  	if(screenService.check_login(principal)) {
			model.addAttribute("user",ScreenService.user_email);
	    	model.addAttribute("admin",ScreenService.user_admin);
		}
		
	    if(pagesize == null) {pagesize=TicketService.pageSize;}else {
	    	  TicketService.pageSize=pagesize;
	    }
	    if(!sort.equals(TicketService.sort)) {
	      sortascending=Boolean.parseBoolean(TicketService.sortascendingDefault);;
	      TicketService.sort=sort;
	    }
	      
    	TicketsCreationDto ticketsForm = new TicketsCreationDto();
        Slice<Ticket> slice = ticketService.findAllTickets(filter, sort, status, "", sortascending, page);
        List<Ticket> tickets = slice.getContent();

        for (int i = 0; i <= tickets.size()-1; i++) {
            ticketsForm.addTicket(tickets.get(i));
        }
	    model.addAttribute("form", ticketsForm);
	    
	      model.addAttribute("tickets", slice.getContent());
	      model.addAttribute("sort", sort);
	      model.addAttribute("sortascending", sortascending);
	      model.addAttribute("filter", filter);
	      model.addAttribute("pagesize", pagesize);
	      model.addAttribute("page", slice.getNumber());
	      model.addAttribute("hasNext", slice.hasNext());
	      model.addAttribute("hasPrevious", slice.hasPrevious());
	      
	      String showing = (slice.getNumberOfElements()==0) ? " " : String.valueOf(1+slice.getNumber()*slice.getSize())+"-"+
	    	        String.valueOf(slice.getNumber()*slice.getSize()+slice.getNumberOfElements());
	      model.addAttribute("showing", showing);
	    
   	    model.addAttribute("dark_mode",ScreenService.dark_mode);
   	    model.addAttribute("scroll_top",ScreenService.admin_screen_top);
    	return "admin";
        
    }
    
    @PostMapping("/admin/delete")
    //@ResponseBody
    public String delete(@ModelAttribute TicketsCreationDto form, RedirectAttributes redir, @AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
    	SecurityTools.adminAuth(principal.getAttribute("http://role/").toString());   
        logger.debug("outp:{}", form.getTickets().toString());
        ticketService.deleteTickets(form.getTickets());
        redir.addFlashAttribute("success", "Selected ticket(s) deleted");
        return "redirect:/admin";
        
    }
	
    @GetMapping(path="/admin/open/{id}")
    public String adminTicketPage(@PathVariable(required = false) Integer id ,HttpServletRequest request, Model model, @AuthenticationPrincipal OAuth2User principal) throws JsonMappingException, JsonProcessingException {
    	ScreenService.admin_screen_top=false;
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
        return "admin_ticket";
    }
    
    @PostMapping(path="/admin/update")
    public String adminUpdateTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir) {
      //logger.debug("outp-ticket_update: {}",ticket.toString());
      ticket.setUpdated(new Date());
      ticketService.updateTicket(ticket);
      redir.addFlashAttribute("success", "ticket updated");
      return "redirect:/admin/open/"+ticket.getId().toString();
    }
    
    @PostMapping(path="/admin/close")
    //@ResponseBody
    public String adminCloseTicket(@ModelAttribute Ticket ticket, RedirectAttributes redir, Model model) {
      //redir.addFlashAttribute("ticket", ticketService.getTicket(ticket.getId()));
      Ticket t=ticketService.getTicket(ticket.getId());
      t.setStatus("Closed");
      ticketService.saveTicket(t);
      redir.addFlashAttribute("success", "ticket closed");
      return "redirect:/admin/open/"+ticket.getId().toString();
    }
}