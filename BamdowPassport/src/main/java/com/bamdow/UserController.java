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
import org.springframework.web.bind.annotation.ResponseBody;

import com.bamdow.service.TokenService;

@Controller
public class UserController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/getuserinfo.do")
	@ResponseBody
	public String getuserinfo(String token){
		Map<String, String> tokenMap = (Map<String, String>) tokenService.get(token);
		String username = tokenMap.get("username");
		long expire = tokenService.getExpire(token);
		String json= "{\"error\":\"invalidate token\"}";
		if( username==null || expire==-2 ){
			return json;
		}
		json="{\"username\":\""+username+"\",\"expire\":\""+username+"\"}";
		return json;
	}
	
}
