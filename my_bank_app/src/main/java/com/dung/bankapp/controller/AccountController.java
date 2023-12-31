package com.dung.bankapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dung.bankapp.exception.CustomRestfullException;
import com.dung.bankapp.repository.entity.User;

@Controller
@RequestMapping("/account") 
public class AccountController {

	@Autowired
	private HttpSession session;
	
	// 임시 예외 발생 확인 http://localhost:80/account/list
	@GetMapping({"/list", "/"})
	public String list() {
		
		// 인증 검사 
		User principal = (User)session.getAttribute("principal");
		if(principal == null) {
			throw new CustomRestfullException("인증된 사용자가 아닙니다", 
					HttpStatus.UNAUTHORIZED);
		}
		
		return "account/list";
	}
	
}