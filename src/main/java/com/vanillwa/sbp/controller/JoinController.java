package com.vanillwa.sbp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanillwa.sbp.dto.JoinDTO;
import com.vanillwa.sbp.service.JoinService;

import lombok.RequiredArgsConstructor;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {
	
	private final JoinService joinService;
	
	@PostMapping("/join")
	public String join(@RequestBody JoinDTO joinDTO) {
		System.out.println("username : " + joinDTO.getUsername());
		System.out.println("nickname : " + joinDTO.getNickname());
		System.out.println("password : " + joinDTO.getPassword());
		joinService.createUser(joinDTO);
		
		return "ok";
	}
	
}
