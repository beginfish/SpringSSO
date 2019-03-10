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
	public ModelAndView one(String token,String logout,String orign,HttpServletRequest req, HttpServletResponse resp){
		//退出登录
		if( "-1".equals(logout) ){
			String cookied = tokenService.get(token);
			tokenService.remove(token);
			tokenService.remove(cookied);
			return null;
		}
		if( token!=null && !"".equals(token) ){
			String cookieId= UUID.randomUUID().toString();
			int maxage = 8*60;//8分钟小于passport10分钟
			Cookie cookie = new Cookie("token",cookieId);
			cookie.setHttpOnly(true);
			cookie.setMaxAge( maxage );
			resp.addCookie(cookie);
			tokenService.put(cookieId, token,maxage);
			tokenService.put(token, cookieId,maxage);
			try {
				resp.sendRedirect(orign);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
