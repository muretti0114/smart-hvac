package org.kobelig.device.sensor.impl;

import org.kobelig.device.type.HumiditySensor;
import org.kobelig.state.DeviceStatus;

/**
 * An implementation of HumiditySensor, which uses a humidity sensor of SensorBoxService
 * @author masa-n
 *
 */
public class SBSHumidityAdapter implements HumiditySensor {
	private SensorBox sbox = null;         //Stub of SensorBoxService
	private String sensorId = null;        //ID of the humidity sensor in the box
	private String id = null;

	/**
	 * Construct a humidity sensor using a humidity sensor of SensorBoxService
	 * @param name      Unique name used in the appliation.
	 * @param url       URL of SensorBoxService
	 * @param sensorId  ID of the humidity sensor used in the sensorbox
	 */
	public SBSHumidityAdapter(String url, String sensorId) {
		sbox = new SensorBox(url);  //Create a stub based on URL
		this.sensorId = sensorId;
		this.id = sbox.getInfo(sensorId, "boxId") + "." + sensorId;
	}

	@Override
	public double getHumidity() {
		return sbox.getValue(sensorId);
	}

	@Override
	public DeviceStatus getStatus() {
		DeviceStatus s = new DeviceStatus();
		s.addValue("id", id);
		s.addValue("humidity", Double.toString(getHumidity()));
		return s;
	}


	@Override
	public String getId() {
		// TODO 自動生成されたメソッド・スタブ
		return id;
	}



}
