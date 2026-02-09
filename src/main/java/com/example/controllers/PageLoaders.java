package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageLoaders {
	
	@GetMapping({"/", "/index"})
	public String index() {
		return "index";
	}
	
}
