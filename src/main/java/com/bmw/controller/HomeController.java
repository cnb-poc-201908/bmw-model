package com.bmw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

	@GetMapping("/")
	public String index() throws JsonProcessingException {
		return "redirect:swagger-ui.html";
	}

}
