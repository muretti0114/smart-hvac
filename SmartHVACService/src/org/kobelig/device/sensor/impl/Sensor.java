package org.kobelig.device.sensor.impl;

public abstract class Sensor {
	protected String id;
	public abstract double getValue();

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}



}
