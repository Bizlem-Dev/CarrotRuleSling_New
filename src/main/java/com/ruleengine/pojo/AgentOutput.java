package com.ruleengine.pojo;

public class AgentOutput {

	private String agentsales;
	private String output;
	private String clubbingsales;
	public AgentOutput() {
		
	}
	public AgentOutput(String agentsales,String clubbingsales) {
		this.agentsales = agentsales;
		this.clubbingsales = clubbingsales;
	}
	
	public AgentOutput(String agentsales, String output, String clubbingsales) {
		this.agentsales = agentsales;
		this.output = output;
		this.clubbingsales = clubbingsales;
	}
	public String getAgentsales() {
		return agentsales;
	}
	public String getOutput() {
		return output;
	}
	public String getClubbingsales() {
		return clubbingsales;
	}
	
	
	
}
