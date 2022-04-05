package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 활용 -> 기본폴더 = src/main/resources
		// 뷰리졸버 설정 : templates(prefix), .mustache(suffix)
		return "index"; // src/main/resources/templates/index.mustache
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	//스프링시큐리티 - securityConfig 작성후에는 작동 x
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm"; 
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm"; 
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user); //회원가입 잘되지만, 시큐리티로 로그인 하려면 비밀번호 암호화가 필요함
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN") //@EnableGlobalMethodSecurity 태그가 securityConfig에 지정되어야 하며 해당 권한을 가진 사람만 접근 가능
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //@EnableGlobalMethodSecurity 태그가 securityConfig에 지정되어야 하며
														//해당 메소드가 실행되기 이전에 먼저 실행됨, 여러개를 걸고 싶으면 이렇게
	//@PostAuthorize() // 해당 메소드가 실행되고 이후에 실행됨
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
	
}
