package com.ruleengine.pojo;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceldataSet.
 */
public class ExceldataSet {

	/** The key. */
	private String key = null;
	
	/** The value. */
	private String value = null;
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("ExceldataSet [key=%s, value=%s]", key, value);
	}
	
	
	
}
