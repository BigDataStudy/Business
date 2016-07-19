package com.study.bigdata.simulation;

public class Message {
	
	private String key;
	public Message(String valueOf, String string) {
		this.key = valueOf;
		this.body= string;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	private String body;
}
	

