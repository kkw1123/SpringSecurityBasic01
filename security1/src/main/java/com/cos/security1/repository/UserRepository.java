package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IoC된다. 이유는 JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<User, Integer> {
	//findby는규칙 -> Username은 문법
	//select * from user where username=? 쿼리를 만든 것
	public User findByUsername(String username); //Jpa query methods 를 더 검색해 공부 할 것
	
	//select * from user where email=? 쿼리를 만든 것
	//public User findByEmail(String email);
}
