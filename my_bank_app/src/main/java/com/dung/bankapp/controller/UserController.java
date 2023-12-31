package com.dung.bankapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dung.bankapp.dto.SignInFormDto;
import com.dung.bankapp.dto.SignUpFormDto;
import com.dung.bankapp.exception.CustomRestfullException;
import com.dung.bankapp.repository.entity.User;
import com.dung.bankapp.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired //DI 처리 
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	
	// 회원 가입 페이지 요청 
	// http://localhost:80/user/sign-up
	@GetMapping("/sign-up")
	public String signUp() {
		return "user/signUp";
	}
	
	// 로그인 페이지 요청 
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}
	
	/**
	 * 회원 가입 처리 
	 * @param dto 
	 * @return 리다이렉트 로그인 페이지 
	 */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto dto) {
		
		// 1. 유효성 검사 
		if(dto.getUsername() == null || dto.getUsername().isEmpty() ) {
			throw new CustomRestfullException("username을 입력하세요",
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty() ) {
			throw new CustomRestfullException("password을 입력하세요",
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getFullname() == null || dto.getFullname().isEmpty() ) {
			throw new CustomRestfullException("fullname을 입력하세요",
					HttpStatus.BAD_REQUEST);
		}
		
		userService.signUp(dto);

		return "redirect:/user/sign-in";
	}
	
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto dto) {
		// 1. 유효성 검사 
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하시오", 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password을 입력하시오", 
					HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 호출 
		User principal = userService.signIn(dto);
		httpSession.setAttribute("principal", principal); // 세션에 저장 
		
		return "redirect:/account/list";
	}
}