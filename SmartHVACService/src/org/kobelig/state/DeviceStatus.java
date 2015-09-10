package org.kobelig.state;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Status of a device represented by key-value of attributes.
 * @author masa-n
 *
 */
public class DeviceStatus {
	private TreeMap<String,String> state = new TreeMap<String,String>();

	/**
	 * Create a status.
	 */
	public DeviceStatus() {

	}

	/**
	 * Create a state with a set of keys.
	 * @param keys a set of keys
	 */
	public DeviceStatus(String[] keys) {
		for (String k: keys) {
			addValue(k, "undef");
		}
	}

	/**
	 * Obtain a set of keys in the state.
	 * @return a set of keys in the state
	 */
	public String [] getKeySet() {
		Set<String> s = state.keySet();
		return s.toArray(new String[0]);
	}

	/**
	 * Set a value to a given key in the state.
	 * @param key key in the state
	 * @param value value to be set corresponding to the key
	 */
	public void setValue(String key, String value) {
		if (state.containsKey(key)==false) {
			System.err.println("Warning: key "+ key + " does not exist. "
					+ "Adding as a new key.");
		}
		addValue(key, value);
	}

	/**
	 * Add a new value with a new key in the state
	 * @param key
	 * @param value
	 */
	public void addValue(String key, String value) {
		state.put(key, value);
	}


	/**
	 * Get a value of the key in the state.
	 * @param key key in the state
	 * @return value value corresponding to the key
	 */
	public String getValue(String key) {
		if(state.containsKey(key)) {
			return state.get(key);
		} else {
			System.err.println("Key " + key +" does not exist.");
			return null;
		}
	}

	/**
	 * Obtain a string vector representation for debug.
	 */
	public String toString() {
		String str;
		str = "{ ";
		//concatenating key-value's
		for (String k: getKeySet()) {
			str += "\"" + k + "\":\"" + getValue(k) + "\", ";
		}

		str = str.substring(0, str.length()-2);

		str += "}";

		return str;
	}

	/**
	 * Obtain a state as a key-value map
	 * @return
	 */
	public Map <String, String> getState() {
		return state;
	}



	/**
	 * Merge another state into the current state
	 * @param s
	 */
	public void merge(DeviceStatus s) {
		for (String key: s.getKeySet()) {
			addValue(key, s.getValue(key));
		}
	}

}