package org.kobelig.state;

import org.apache.log4j.Logger;


public class WindowStatus implements Status {
	private int openLevel;	//0~100の整数
	private String state;

	public int getOpenLevel() {
		return openLevel;
	}

	public void setOpenLevel(int openLevel) {
		this.openLevel = openLevel;
		if (openLevel < 0 || openLevel > 100) {
			Logger.getLogger(getClass()).error(openLevel + " : openLevel should be between 0 and 100");
			return;
		} else if (openLevel==0) {
			state = "close";
		} else if (openLevel < 70) {
			state = "half-open";
		} else if (openLevel <= 100) {
			state = "open";
		}
	}
	public String getState() {
		return state;
	}


	public void reset() {
		this.setOpenLevel(0);
	}

	@Override
	public String toString() {
		return "WindowStatus [openLevel=" + openLevel + ", state=" + state
				+ "]";
	}

}
