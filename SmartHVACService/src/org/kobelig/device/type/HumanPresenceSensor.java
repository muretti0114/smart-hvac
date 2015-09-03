package org.kobelig.device.type;

public interface HumanPresenceSensor extends Device {
	/**
	 * Get a human presence as true or false.
	 * @return Return true when a human is around.
	 */
	public boolean isHumanPresent();
}
