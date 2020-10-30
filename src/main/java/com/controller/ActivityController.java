package com.controller;

import java.util.Calendar;
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

import com.model.Activity;
import com.model.Ticket;
import com.service.ActivityService;
import com.service.TicketService;

@Controller
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
  
    @PostMapping(path="/activity/save")
    public String saveActivity(@ModelAttribute Activity activity, RedirectAttributes redir, @AuthenticationPrincipal OAuth2User user) {
      activity.setUser(user.getAttribute("email"));
      activity.setCreated(Calendar.getInstance().getTime());
      activityService.saveActivity(activity);
      return "redirect:/ticket/open/"+activity.getTicketId();
    }
    
}