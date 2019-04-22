package com.ruleengine.pojo;

public class ExceldataSet {

	private String key = null;
	private String value = null;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return String.format("ExceldataSet [key=%s, value=%s]", key, value);
	}
	
	
	
}
