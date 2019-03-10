package com.bamdow.intercepter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bamdow.service.TokenService;
import com.bamdow.spring.LoginRequired;

public class LoginIntercepter implements HandlerInterceptor{

	@Autowired
	private TokenService tokenService;
	
	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws Exception {
		
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod) handler;
			LoginRequired o =  hm.getMethodAnnotation(LoginRequired.class);
			if(o==null){
				o =hm.getClass().getAnnotation(LoginRequired.class);
			}
			if( o != null){
				Cookie[] cookies = req.getCookies();
				String token = null;
				if(cookies!=null){
					for(Cookie cookie:cookies){
						if("token".equals(cookie.getName())){
							token = cookie.getValue();
						}
					}
				}
				String currentUrl = req.getRequestURL().toString();
				String currentUri = req.getRequestURI();
				String host = currentUrl.replaceAll(currentUri, "");
				StringBuffer sb = new StringBuffer(2048);
				String orign = java.net.URLEncoder.encode(currentUri,"utf-8");
				sb.append(host);
				sb.append(req.getContextPath()).append("/notify.do");
				sb.append("?orign=").append(orign);
				String url = java.net.URLEncoder.encode(sb.toString(),"utf-8");
				if(token==null || tokenService.get(token) ==null ||"".equals(tokenService.get(token))){
					try {
						resp.sendRedirect("http://localhost:8081/bamdowPassport/sso.do?originurl="+url);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return false;
				}
				System.out.println("token="+tokenService.get(token));
			}
		}
		
		return true;
	}

}
