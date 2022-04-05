package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴
//로그인 진행이 완료되면 시큐리티 session을 만들어준다.
//기존의 session + 시큐리티 만의 session영역이 존재함(security ContextHolder)
//오브젝트 타입이 정해져있음 => Authentication 타입 객체
//Authentication 안에 User 정보가 있어야 됨.
//User 오브젝트 타입 => UserDetails 타입 객체

//Security Session => 여기 들어갈 수 있는 객체가 Authentication 객체 => 여기에 유저정보를 저장할 때 UserDetails 타입을 사용함
//Security Session=>Authentication=>UserDetails(PrincipalDetails)

public class PrincipalDetails implements UserDetails{
//기본 자바 
//Extends는 부모의 메소드를 그대로 사용
//implements는 부모의 메소드를 오버라이딩(재정의) 하여 사용	
	
	private User user; //콤포지션
	
	public PrincipalDetails(User user) {
		this.user=user;
	}
	
	//해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정 만료여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//계정 잠김여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//계정 비밀번호 사용기간여부, 1년이 지남 같은거
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정 활성화여부
	@Override
	public boolean isEnabled() {
		
		//사이트 내에서 1년동안 회원이 로그인 안하면 휴면계정으로 전환 아래 예를 들어보면
		//현재시간 - 마지막 로그인 시간으로 계산해서 1년 초과시에 return을 false로 바꾸는 것
		
		return true;
	}
}
