package com.bamdow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bamdow.service.TokenService;
import com.bamdow.util.HttpUtil;


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
	public ModelAndView loginout(String originurl,HttpServletRequest req, HttpServletResponse resp){
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
			String token = (String)tokenService.get(cookie_token);
			Map<String, String> tokenMap = (Map<String, String>) tokenService.get(token);
			tokenService.remove(token);
			tokenService.remove(cookie_token);
			for(String key:tokenMap.keySet()){
				if("username".equals(key)){
					continue;
				}
				try{
					HttpUtil.doGet(key+"&logout=-1&token="+token);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		if( originurl!=null && !"".equals(originurl)){
			try {
				resp.sendRedirect(originurl);
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		return new ModelAndView("loginout");
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
		cookie.setMaxAge(maxage);
		resp.addCookie(cookie);
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put(origin, "1");
		tokenMap.put("username", username);
		tokenService.put(cookeid, token,maxage);
		tokenService.put(token,tokenMap,maxage);
		try {
			resp.sendRedirect(origin+"&token="+token);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
