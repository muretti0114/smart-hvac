package org.kobelig.device.sensor.impl;

import org.kobelig.device.type.TemperatureSensor;
import org.kobelig.state.DeviceStatus;

/**
 * An implementation of TemperatureSensor, which uses a temperature sensor of SensorBoxService
 * @author masa-n
 *
 */
public class SBSTemperatureAdapter implements TemperatureSensor {
	private SensorBox sbox = null;         //Stub of SensorBoxService
	private String sensorId = null;        //ID of the humidity sensor in the box
	private String id = null;

	/**
	 * Construct a temperature sensor using a motion sensor of SensorBoxService
	 * @param name      Unique name used in the appliation.
	 * @param url       URL of SensorBoxService
	 * @param sensorId  ID of the temperature sensor used in the sensorbox
	 */

	public SBSTemperatureAdapter(String name, String url, String sensorId) {
		this.id = name;
		sbox = new SensorBox(url);  //Create a stub based on URL
		this.sensorId = sensorId;
	}

	@Override
	public double getTemperature() {
		return sbox.getValue(sensorId);
	}


	@Override
	public DeviceStatus getStatus() {
		DeviceStatus s = new DeviceStatus();
		s.addValue("id", id);
		s.addValue("Temperature", Double.toString(getTemperature()));
		return s;
	}

	@Override
	public String getId() {
		// TODO 自動生成されたメソッド・スタブ
		return id;
	}



}
