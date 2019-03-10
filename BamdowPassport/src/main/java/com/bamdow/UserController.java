package com.bamdow;

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
		String json = tokenService.get(token);
		return json;
	}
	
	@RequestMapping("/expire.do")
	@ResponseBody
	public String expire(String token){
		String json = tokenService.get(token);
		return json;
	} 
	
	
}
