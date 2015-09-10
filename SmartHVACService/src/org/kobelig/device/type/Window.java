package org.kobelig.device.type;

public interface Window extends Device {
	/**
	 * Open the window.
	 */
	public void open() throws DeviceOperationException;

	/**
	 * Close the window.
	 */
	public void close() throws DeviceOperationException;

	/**
	 * Partly open the window with the designted level between 0 and 100.
	 * @param level a value from 0 to 100
	 */
	public void partlyOpen(int level) throws DeviceOperationException;

}
