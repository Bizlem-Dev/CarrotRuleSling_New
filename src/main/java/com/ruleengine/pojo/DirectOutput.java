package com.ruleengine.pojo;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectOutput.
 */
public class DirectOutput {

	/** The directsales. */
	private String directsales;
	
	/** The output. */
	private String output;
	
	/** The clubbingsales. */
	private String clubbingsales;
	
	/**
	 * Instantiates a new direct output.
	 */
	public DirectOutput() {}
	
	/**
	 * Instantiates a new direct output.
	 *
	 * @param directsales the directsales
	 * @param output the output
	 * @param clubbingsales the clubbingsales
	 */
	public DirectOutput(String directsales, String output, String clubbingsales) {
		this.directsales = directsales;
		this.output = output;
		this.clubbingsales = clubbingsales;
	}
	
	/**
	 * Instantiates a new direct output.
	 *
	 * @param directsales the directsales
	 * @param clubbingsales the clubbingsales
	 */
	public DirectOutput(String directsales,String clubbingsales) {
		this.directsales = directsales;
		this.clubbingsales = clubbingsales;
	}
	
	/**
	 * Gets the directsales.
	 *
	 * @return the directsales
	 */
	public String getDirectsales() {
		return directsales;
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
