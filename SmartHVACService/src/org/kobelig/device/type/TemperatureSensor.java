package org.kobelig.device.type;

public interface TemperatureSensor extends Device{
	/**
	 * Get temperature in celcius.
	 * @return temperature
	 */
	public double getTemperature();
}
