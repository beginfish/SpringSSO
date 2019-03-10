package com.bamdow.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	private ConcurrentMap<String, String> tokenMap = new ConcurrentHashMap<String, String>();
	private ConcurrentMap<TokenKey, AtomicInteger> expireMap = new ConcurrentHashMap<TokenKey, AtomicInteger>();
	
	public String put(String key,String value){
		return tokenMap.putIfAbsent(key, value);
	}
	
	public String get(String key){
		return tokenMap.get(key);
	}
	
	public String remove(String key){
		return tokenMap.remove(key);
	}
	
	public void setExpire(String key,Integer seconds){
		AtomicInteger af =new AtomicInteger();
		//af.addAndGet(delta)
	}
}
