package com.bamdow.service;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class TokenService extends TimerTask{

	private ConcurrentMap<String, Object> tokenMap = new ConcurrentHashMap<String, Object>();
	private ConcurrentMap<String, Long> expireMap = new ConcurrentHashMap<String, Long>();
	private final static long overdue = -2;
	private final static long persisted = -1;
	
	public boolean containsKey(String key){
		if( !validateExpire(key) ) return false;
		return tokenMap.containsKey(key);
	}
	
	public Object put(String key,Object value){
		return checkput(key, value,persisted);
	}
	
	public Object put(String key,Object value,long expire){
		if( expire<=0 ){
			throw new IllegalArgumentException("expire can not less than 0");
		}
		return checkput(key, value, expire);
	}
	
	public Object putIfAbsent(String key,Object value){
		return checkputIfAbsent(key, value,persisted);
	}
	
	public String putIfAbsent(String key,Object value,long expire){
		if( expire<=0 ){
			throw new IllegalArgumentException("expire can not less than 0");
		}
		return putIfAbsent(key, value, expire);
	}
	
	private Object checkputIfAbsent(String key,Object value,long expire){
		Object  val = tokenMap.putIfAbsent(key, value);
		if(( value!=null && value instanceof String && value.equals(val) ) || value==val  ){
			setExpire(key, expire);
		}
		return val;
	}
	
	private Object checkput(String key,Object value,long expire){
		Object val = tokenMap.put(key, value);
		setExpire(key, expire);
		return val;
	}
	public Object get(String key){
		if( !validateExpire(key) ) return null;
		return tokenMap.get(key);
	}
	
	public Object remove(String key){
		expireMap.remove(key);
		return tokenMap.remove(key);
	}
	
	public void setExpire(String key,long expire){
		if( expire != persisted){
			expireMap.put(key, System.currentTimeMillis()+expire*1000);
		}else{
			expireMap.put(key, expire);
		}		
	}
	
	public long getExpire(String key){
		Long old = expireMap.get(key);
		if( old != null && old.intValue() == persisted){
			return persisted;
		}
		long expire = (expireMap.get(key)-System.currentTimeMillis())*1000;
		if( expire<0 ){
			expire = overdue;
			remove(key);
		}
		return expire;
	}

	private boolean validateExpire(String key){
		Long old = expireMap.get(key);
		if( old==null ) return false;
		if( old != null && old.intValue() == persisted){
			return true;
		}
		long expire = (expireMap.get(key)-System.currentTimeMillis())*1000;
		return expire>0?true:false;
	}

	@Override
	public void run() {
		 for( String key : expireMap.keySet() ){
			 if(!validateExpire(key)){
				 System.out.println("begin to remove:"+key);
				 remove(key);
			 }
		 }
	}
}
