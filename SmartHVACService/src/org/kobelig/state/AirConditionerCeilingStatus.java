package org.kobelig.state;

import java.io.Serializable;
@SuppressWarnings("serial")
public class AirConditionerCeilingStatus implements Serializable ,Status{

	/**on/off*/
	boolean	power;
	/**運転モード*/
	String	operationalStatus;
	/**風力*/
	int	windLevel;
	/**温度*/
	int	temperature;
	/**swingしているかどうか*/
	int windDirection;
	/**タイマーのon/off*/
	String timer;
	/**換気*/
	boolean ventilation;


	public boolean isVentilation() {
		return ventilation;
	}

	public void setVentilation(boolean ventilation) {
		this.ventilation = ventilation;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	public String getTimer(){
		return timer;
	}

	public boolean getPower() {
		return power;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	public String getOperationalStatus() {
		return operationalStatus;
	}

	public int getWindLevel() {
		return windLevel;
	}

	public void setWindLevel(int windLevel) {
		this.windLevel = windLevel;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}

	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}


	public void reset() {
		this.setPower(false);
		this.setOperationalStatus("off");
		this.setTemperature(0);
		this.setWindDirection(0);
		this.setWindLevel(0);
	}

	@Override
	public String toString() {
		return "AirConditionerCeilingStatus [power=" + power
				+ ", operationalStatus=" + operationalStatus + ", windLevel="
				+ windLevel + ", temperature=" + temperature
				+ ", windDirection=" + windDirection + ", timer=" + timer
				+ ", ventilation=" + ventilation + "]";
	}

	public void printStatus(){
		System.out.println("power "+power);
		System.out.println("operationalStatus "+operationalStatus);
		System.out.println("windLevel "+windLevel);
		System.out.println("temperature "+temperature);
		System.out.println("windDirection "+windDirection);

	}

}