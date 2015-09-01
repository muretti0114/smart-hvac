package org.kobelig.device.appliance;

import org.apache.log4j.Logger;
import org.kobelig.state.WindowStatus;


public class WindowController extends Appliance {
	WindowStatus status = new WindowStatus();
	//ACDProxy window = new ACDProxy();   // The window has no actuator currently.

	/**
	 * Construct a window with given id
	 * @param id the identifier of the window
	 */
	public WindowController(String id){
		super();
		setId(id);
		fileName = id + "@Window.ini";

		status = (WindowStatus)super.loadStatus(status, fileName);

	}
	/**
	 * Open the window to fully opened.
	 * @return true if succeeded
	 */
	public boolean open() {
		Logger.getLogger(getClass()).info("Window " + id + " is opened");
		boolean cond = slideWindowAt(100);
		super.saveStatus(status, fileName);
		return cond;
	}

	/**
	 * Close the window to fully closed
	 * @return true if succeeded
	 */
	public boolean close() {
		Logger.getLogger(getClass()).info("Window " + id + " is closed");
		boolean cond = slideWindowAt(0);
		super.saveStatus(status, fileName);
		return cond;
	}

	/**
	 * Slide the window to the given level
	 * @param level percentage to the full-open (0 to 100)
	 * @return true if succeeded
	 */
	public boolean slideWindowAt(int level) {

		if (level<0 || level>100) {
			Logger.getLogger(getClass()).error(level+": Window level must be between 0 and 100");
			return false;
		} else {
			Logger.getLogger(getClass()).info("Level of window " + id + " is set to " + level);
			status.setOpenLevel(level);
			super.saveStatus(status, fileName);
			return true;
		}
	}

	public WindowStatus getStatus() {
		return status;
	}


}
