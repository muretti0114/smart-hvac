package org.kobelig.device.type;

import org.kobelig.state.DeviceStatus;

public interface Device {
	public String getId();
	public DeviceStatus getStatus();

}
