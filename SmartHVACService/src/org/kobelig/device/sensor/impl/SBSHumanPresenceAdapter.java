package org.kobelig.device.sensor.impl;

import org.kobelig.device.type.HumanPresenceSensor;
import org.kobelig.state.DeviceStatus;

/**
 * An implementation of HumanPresenceSensor, which uses a motion sensor of SensorBoxService
 * @author masa-n
 *
 */
public class SBSHumanPresenceAdapter implements HumanPresenceSensor {
	private SensorBox sbox = null;         //Stub of SensorBoxService
	private String sensorId = null;        //ID of the humidity sensor in the box
	private String id=null;

	/**
	 * Construct a presence sensor using a motion sensor of SensorBoxService
	 * @param name      Unique name used in the appliation.
	 * @param url       URL of SensorBoxService
	 * @param sensorId  ID of the motion sensor used in the sensorbox
	 */

	public SBSHumanPresenceAdapter(String name, String url, String sensorId) {
		this.id = name;
		sbox = new SensorBox(url);  //Create a stub based on URL
		this.sensorId = sensorId;
	}

	@Override
	public boolean isHumanPresent() {
		double val = sbox.getValue(sensorId);
		if (val==0.0) {
			return false;
		} else {
			return true;
		}
	}


	@Override
	public DeviceStatus getStatus() {
		DeviceStatus s = new DeviceStatus();
		s.addValue("id", id);
		s.addValue("Motion", Boolean.toString(isHumanPresent()));
		return s;
	}

	@Override
	public String getId() {
		// TODO 自動生成されたメソッド・スタブ
		return id;
	}


}