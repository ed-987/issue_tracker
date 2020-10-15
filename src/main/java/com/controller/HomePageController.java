package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomePageController {

	@GetMapping(value= "/homepage")
	@ResponseBody
	public String homePage() {
		return "issue tracker test";
	}
}