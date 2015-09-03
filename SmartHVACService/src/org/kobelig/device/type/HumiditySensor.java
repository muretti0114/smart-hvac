package org.kobelig.device.type;

public interface HumiditySensor extends Device{
	/**
	 * Get humidity in percentage.
	 * @return humidity in percentage (0-100%)
	 */
	public double getHumidity();
}
