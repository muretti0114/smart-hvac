package org.kobelig.service;

import org.kobelig.device.type.AirConditioner;
import org.kobelig.device.type.Circulator;
import org.kobelig.device.type.HumanPresenceSensor;
import org.kobelig.device.type.HumiditySensor;
import org.kobelig.device.type.Radiator;
import org.kobelig.device.type.TemperatureSensor;
import org.kobelig.device.type.Window;
import org.kobelig.state.State;

public class HVACService extends SmartService {

	// Devices used in the HVAC service
	private TemperatureSensor temperatureSensor;
	private HumiditySensor humiditySensor;
	private HumanPresenceSensor humanPresenceSensor;
	private AirConditioner airConditioner;
	private Radiator radiator;
	private Circulator circulator;
	private Window window;

	// Threshould of Discomfort Index used for service execution
	public double upperDI = 70.0;
	public double lowerDI = 65.0;


	public TemperatureSensor getTemperatureSensor() {
		return temperatureSensor;
	}

	public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
		this.temperatureSensor = temperatureSensor;
	}

	public HumiditySensor getHumiditySensor() {
		return humiditySensor;
	}

	public void setHumiditySensor(HumiditySensor humiditySensor) {
		this.humiditySensor = humiditySensor;
	}

	public HumanPresenceSensor getHumanPresenceSensor() {
		return humanPresenceSensor;
	}

	public void setHumanPresenceSensor(HumanPresenceSensor humanPresenceSensor) {
		this.humanPresenceSensor = humanPresenceSensor;
	}

	public AirConditioner getAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(AirConditioner airConditioner) {
		this.airConditioner = airConditioner;
	}

	public Radiator getRadiator() {
		return radiator;
	}

	public void setRadiator(Radiator radiator) {
		this.radiator = radiator;
	}

	public Circulator getCirculator() {
		return circulator;
	}

	public void setCirculator(Circulator circulator) {
		this.circulator = circulator;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}



	@Override
	public State observeState() {

		//Getting a device status from each device.
		State s = new State();

		s.addDeviceStatus(temperatureSensor.getId(), temperatureSensor.getStatus());
		s.addDeviceStatus(humiditySensor.getId(), humiditySensor.getStatus());
		s.addDeviceStatus(humanPresenceSensor.getId(), humanPresenceSensor.getStatus());
		s.addDeviceStatus(airConditioner.getId(), airConditioner.getStatus());
		s.addDeviceStatus(radiator.getId(), radiator.getStatus());
		s.addDeviceStatus(circulator.getId(), circulator.getStatus());
		s.addDeviceStatus(window.getId(), window.getStatus());

		System.out.println(s);

		return s;
	}

	@Override
	public Context interpreteState(State state) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean isAcceptable(Context context) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public Action[] proposeActions(Context context) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void executeActions(Action[] actions) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
