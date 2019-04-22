package com.ruleengine.pojo;

public class DirectOutput {

	private String directsales;
	private String output;
	private String clubbingsales;
	public DirectOutput() {}
	public DirectOutput(String directsales, String output, String clubbingsales) {
		this.directsales = directsales;
		this.output = output;
		this.clubbingsales = clubbingsales;
	}
	public DirectOutput(String directsales,String clubbingsales) {
		this.directsales = directsales;
		this.clubbingsales = clubbingsales;
	}
	public String getDirectsales() {
		return directsales;
	}
	public String getOutput() {
		return output;
	}
	public String getClubbingsales() {
		return clubbingsales;
	}
	
}
