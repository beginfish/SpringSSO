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


@Controller
public class LoginController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/login.do")
	public ModelAndView login(String error,String origin){
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("origin", origin);
		if( error!=null && !"".equals(error)){
			mv.addObject("error", error);
		}
		return mv;
	}
	
	@RequestMapping("/loginout.do")
	public ModelAndView loginout(HttpServletRequest req){
		Cookie[] cookies = req.getCookies();
		String cookie_token = null;
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("lg-token".equals(cookie.getName())){
					cookie_token = cookie.getValue();
				}
			}
		}
		
		if(cookie_token != null){
			String token = tokenService.get(cookie_token);
			tokenService.remove(token);
			tokenService.remove(cookie_token);
		}
		ModelAndView mv = new ModelAndView("loginout");
		return mv;
	}
	
	@RequestMapping("/dologin.do")
	public ModelAndView dologin(String username,String password,String origin,HttpServletRequest req, HttpServletResponse resp){
		if(!("test".equals(username) && "test".equals(password))){
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("origin", origin);
			mv.addObject("error", "用户名密码错误");
			return mv;
		}
		String cookeid= UUID.randomUUID().toString();
		String token = UUID.randomUUID().toString();
		int maxage = 10*60;
		Cookie cookie = new Cookie("lg-token",cookeid);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(10*60);
		resp.addCookie(cookie);
		tokenService.put(cookeid, token);
		String json="{\"username\"：\""+username+"\",\"timeout\"：\""+maxage+"\"}";
		tokenService.put(token,json);
		try {
			resp.sendRedirect(origin+"&token="+token);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
