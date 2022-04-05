package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// view 기본 설정 변경, 리졸버란 view이름으로부터 사용될 view 객체를 맵핑하는 역할
		// 이를 오버라이딩으로 재설정 하는 것
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setPrefix("classpath:/templates/"); //접두사 고정
		resolver.setSuffix(".html"); //접미사 고정, mustache가 아닌 html로 인식하게 됨
		
		registry.viewResolver(resolver);
	}
}
