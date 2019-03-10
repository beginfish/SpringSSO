package com.bamdow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bamdow.spring.LoginRequired;


@Controller
public class HelloController {
	
	@RequestMapping("/one.do")
	@LoginRequired  
	public ModelAndView one(){
		return new ModelAndView("one");
	}
	
	@RequestMapping("/hello.do")
	public ModelAndView hello(){
		return new ModelAndView("hello");
	}
	
	
	
}
