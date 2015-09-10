package org.kobelig.device.type;

public interface Circulator extends Device{
	/**
	 * Turn on the device.
	 */
	public void on() throws DeviceOperationException;
	/**
	 * Turn off the device.
	 */
	public void off() throws DeviceOperationException;
	/**
	 * Set the direction of the circulator to NORMAL(0), UP(1), DOWN(2)
	 * @param dir NORMAL(0), UP(1), DOWN(2)
	 */
	public void setDirection(int dir) throws DeviceOperationException;
	/**
	 * Set the speed of the circulator fan to Low (0), Medium (1) or High (2)
	 * @param speed Low (0), Medium (1) or High (2)
	 */
	public void setSpeed(int speed) throws DeviceOperationException;
}
