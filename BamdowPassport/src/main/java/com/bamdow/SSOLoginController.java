package com.bamdow;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bamdow.service.TokenService;

@Controller
public class SSOLoginController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/sso.do")
	public void dologin(String originurl,HttpServletRequest req, HttpServletResponse resp){
		Cookie[] cookies = req.getCookies();
		String cookie_token = null;
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("lg-token".equals(cookie.getName())){
					cookie_token = cookie.getValue();
				}
			}
		}
		
		String token = null;
		if(cookie_token!=null){
			token = (String) tokenService.get(cookie_token);
		}
		//不存在跳转登录
		if(cookie_token==null || token ==null || "".equals(token)){
			try {
				String url = java.net.URLEncoder.encode(originurl,"utf-8");
				resp.sendRedirect(req.getContextPath()+"/login.do?origin="+url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			tokenService.setExpire(cookie_token, 10*60);
			Map<String, String> tokenMap = (Map<String, String>) tokenService.get(token);
			tokenMap.put(originurl, "1");
			tokenService.put(token,tokenMap, 10*60);
			//存在跳转登录通知系统
			try {
				resp.sendRedirect(originurl+"&token="+token);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
