package com.bamdow.service;

public class TokenKey {

	private String key="";
	
	private long createtime=0;
	
	public TokenKey(String key){
		this.key = key;
		createtime = System.currentTimeMillis();
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	
}
