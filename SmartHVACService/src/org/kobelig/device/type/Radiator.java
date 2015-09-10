package org.kobelig.device.type;

public interface Radiator extends Device {
	/**
	 * Turn on the device.
	 */
	public void on() throws DeviceOperationException;

	/**
	 * Turn off the device.
	 */
	public void off() throws DeviceOperationException;

	/**
	 * Set the temperature setting of the radiator to temp celcius.
	 * @param temp temperature setting
	 */
	public void setTemperature(double temp) throws DeviceOperationException;

}
