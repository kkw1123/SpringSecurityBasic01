package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화
																		 //prePostEnabled는 preAuthorize, PostAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//해당 메소드의 리턴되는 오브젝트를 IoC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//여기서 권한을 거는건 글로벌로 거는 것이며 secured, preAuthorize를 활용해 개별 메소드에 시큐리티를 걸 수 있다
		//여기까지 내용들이 스프링 시큐리티의 가장 기본 적인 사항, 잘 알아두자
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated() //user 이후의 화면들은 인증이 있어야 가능(인증만되면 들어갈 수 있는 주소)
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //manager 이후의 화면들은 권한이 있어야 가능
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll() //어느 사용자라도 접근 가능
		.and()
		.formLogin()
		.loginPage("/loginForm") // 권한없는 화면으로 이동시 이 화면으로 자동으로 이동됨
		//.usernameParameter("username2") //loginForm에서 name으로 지정된 이름이 정확히 username으로 하거나 
									      //아니면 여기서 지정해주어야 loadUserByUsername으로 넘길 수 있음
		.loginProcessingUrl("/login") //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌(controller에 login을 따로 안만들어도 됨)
		.defaultSuccessUrl("/"); //로그인 성공시 이동되는 화면
	}
}
