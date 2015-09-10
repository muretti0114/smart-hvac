package org.kobelig.device.type;

public interface AirConditioner extends Device{
	/**
	 * Turn on the device.
	 */
	public void on() throws DeviceOperationException;
	/**
	 * Turn off the device.
	 */
	public void off() throws DeviceOperationException;
	/**
	 * Set the temperature setting of the air-conditioner to temp celcius.
	 * @param temp temperature setting
	 */
	public void setTemperature(double temp) throws DeviceOperationException;
	/**
	 * Set the wind level
	 * @param level
	 */
	public void setFanSpeed(int level) throws DeviceOperationException;

}
