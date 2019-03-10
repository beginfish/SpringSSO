package com.bamdow;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bamdow.service.TokenService;
import com.bamdow.spring.LoginRequired;

@Controller
public class NotifyController {
    
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/notify.do")
	public void one(String token,String orign,HttpServletRequest req, HttpServletResponse resp){
		String uid= UUID.randomUUID().toString();
		Cookie cookie = new Cookie("token",uid);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(10*60);
		resp.addCookie(cookie);
		tokenService.put(uid, token);
		try {
			resp.sendRedirect(orign);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
