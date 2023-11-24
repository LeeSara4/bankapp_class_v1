package com.dung.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dung.bankapp.dto.SignInFormDto;
import com.dung.bankapp.dto.SignUpFormDto;
import com.dung.bankapp.exception.CustomRestfullException;
import com.dung.bankapp.repository.entity.User;
import com.dung.bankapp.repository.interfaces.UserRepository;

@Service
public class UserService {

	@Autowired // 의존주입 (생성자, 메서드) 
	private UserRepository userRepository;
	
	@Transactional
	public int signUp(SignUpFormDto dto) { 	
		// username 에 중복 여부 확인 생략 
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.fullname(dto.getPassword())
				.build(); // build() 반드시 호출
		 int resultRowCount = userRepository.insert(user);
		 if(resultRowCount != 1) {
			 throw new CustomRestfullException("회원 가입 실패", 
					 HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 return resultRowCount;
	}
	
	@Transactional
	public User signIn(SignInFormDto dto) {
		User userEntity = userRepository.findByUsernameAndPassword(dto);
		if (userEntity == null) {
			throw new CustomRestfullException("아이디 or 비밀번호가 틀렸습니다."
					, HttpStatus.BAD_REQUEST);
		}
		return userEntity;
	}
}