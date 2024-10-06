package com.vanillwa.sbp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
@ResponseBody
public class MainController {

	@GetMapping("/")
	public String home() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

		System.out.println("authorities : "+ authorities);
		return "Main Controller\n" + "name : " + name;
	}
	
}
