package org.kobelig.service;

import java.util.HashMap;
import java.util.Set;

/**
 * Represent a state used by the service.
 * A state is represented by a vector of key-value.
 * So it is implemented by HashMap, for conveinience.
 *
 * @author masa-n
 *
 */
public class State {
	private HashMap<String,String> map;


	/**
	 * Create a state.
	 */

	public State() {

	}

	/**
	 * Create a state with a set of keys.
	 * @param keys a set of keys
	 */
	public State(String[] keys) {
		map = new HashMap<String,String>();
		for (String k: keys) {
			setValue(k, "undef");
		}
	}

	/**
	 * Obtain a set of keys in the state.
	 * @return a set of keys in the state
	 */
	public String [] getKeySet() {
		Set<String> s = map.keySet();
		return s.toArray(new String[0]);
	}

	/**
	 * Set a value to a given key in the state.
	 * @param key key in the state
	 * @param value value to be set corresponding to the key
	 */
	public void setValue(String key, String value) {
		if (map.containsKey(key)==false) {
			System.err.println("Warning: key "+ key + " does not exist. "
					+ "Adding as a new key.");
		}
		map.put(key, value);
	}

	/**
	 * Get a value of the key in the state.
	 * @param key key in the state
	 * @return value value corresponding to the key
	 */
	public String getValue(String key) {
		if(map.containsKey(key)) {
			return map.get(key);
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
		str = "[ ";
		//concatenating key-value's
		for (String k: getKeySet()) {
			str += k + ":" + getValue(k) + ", ";
		}
		str += "]";

		return str;
	}



}
