package org.kobelig.state;

import java.util.TreeMap;

/**
 * Class that defines a global state consisting of status of all devices
 *
 * @author masa-n
 *
 */
public class State {
	private TreeMap <String, DeviceStatus> map = new TreeMap<String, DeviceStatus>();

	/**
	 * Obtain a status of a device
	 * @param id  ID of a device
	 * @return a status of the device
	 */
	public DeviceStatus getDeviceStatus (String id) {
		return map.get(id);
	}

	public void addDeviceStatus(String id, DeviceStatus status) {
		map.put(id, status);
	}

	public String toString() {
		String str = "";
		for (String key: map.keySet()) {
			DeviceStatus status = map.get(key);
			if (status!=null) {
				str = str + key + ":" + status + "\n";
			}
		}

		return str;
	}



}
