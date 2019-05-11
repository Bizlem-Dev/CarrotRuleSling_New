package com.ruleengine.pojo;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentOutput.
 */
public class AgentOutput {

	/** The agentsales. */
	private String agentsales;
	
	/** The output. */
	private String output;
	
	/** The clubbingsales. */
	private String clubbingsales;
	
	/**
	 * Instantiates a new agent output.
	 */
	public AgentOutput() {
		
	}
	
	/**
	 * Instantiates a new agent output.
	 *
	 * @param agentsales the agentsales
	 * @param clubbingsales the clubbingsales
	 */
	public AgentOutput(String agentsales,String clubbingsales) {
		this.agentsales = agentsales;
		this.clubbingsales = clubbingsales;
	}
	
	/**
	 * Instantiates a new agent output.
	 *
	 * @param agentsales the agentsales
	 * @param output the output
	 * @param clubbingsales the clubbingsales
	 */
	public AgentOutput(String agentsales, String output, String clubbingsales) {
		this.agentsales = agentsales;
		this.output = output;
		this.clubbingsales = clubbingsales;
	}
	
	/**
	 * Gets the agentsales.
	 *
	 * @return the agentsales
	 */
	public String getAgentsales() {
		return agentsales;
	}
	
	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}
	
	/**
	 * Gets the clubbingsales.
	 *
	 * @return the clubbingsales
	 */
	public String getClubbingsales() {
		return clubbingsales;
	}
	
	
	
}
