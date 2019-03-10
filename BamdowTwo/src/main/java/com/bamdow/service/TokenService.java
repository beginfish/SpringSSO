package com.bamdow.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	private ConcurrentMap<String, String> tokenMap = new ConcurrentHashMap<String, String>();
	
	public String put(String key,String value){
		return tokenMap.putIfAbsent(key, value);
	}
	
	public String get(String key){
		return tokenMap.get(key);
	}
	
	public String remove(String key){
		return tokenMap.remove(key);
	}
}
