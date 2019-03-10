package com.bamdow.timer;

import java.util.Timer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bamdow.service.TokenService;

@Service
public class ExpireTimerTask implements InitializingBean{

	@Autowired
	private TokenService tokenService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(tokenService, 10000, 30000);
	}

}
