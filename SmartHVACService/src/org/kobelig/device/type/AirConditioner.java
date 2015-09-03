package org.kobelig.device.type;

public interface AirConditioner extends Device{
	/**
	 * Turn on the device.
	 */
	public void on();
	/**
	 * Turn off the device.
	 */
	public void off();
	/**
	 * Set the temperature setting of the air-conditioner to temp celcius.
	 * @param temp temperature setting
	 */
	public void setTemperature(double temp);
	/**
	 * Set the wind level
	 * @param level
	 */
	public void setFanSpeed(int level);

}
