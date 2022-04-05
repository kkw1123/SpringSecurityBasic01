package com.cos.security1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//시큐리티 설정에서 .loginProcessingUrl("/login") 으로 걸어놨기 때문에
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 자동으로 실행됨(그런 규칙으로 되어있음)

@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	//Session=>Authentication=>UserDetails
	//이 리턴된 UserDetails 값은 Authentication 내부에 들어감
	//최종적으로 Session(내부 Authentication(내부 UserDetails))이런형태로 세션이 생성되면서 로그인이 완료됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username);
		//유저 정보가 있다면 해당 값으로 로그인 진행 없으면 null로 진행
		if(userEntity !=null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
}
