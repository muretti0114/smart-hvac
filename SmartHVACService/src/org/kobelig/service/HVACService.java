package org.kobelig.service;

import java.util.ArrayList;
import java.util.TreeMap;

import org.kobelig.device.type.AirConditioner;
import org.kobelig.device.type.Circulator;
import org.kobelig.device.type.HumanPresenceSensor;
import org.kobelig.device.type.HumiditySensor;
import org.kobelig.device.type.Radiator;
import org.kobelig.device.type.TemperatureSensor;
import org.kobelig.device.type.Window;
import org.kobelig.state.State;

/**
 * Static version of HVAC service. Depending on the environmental context, the service
 * executes appropriate appliances to keep the environment to be comfortable.
 *
 * @author masa-n
 *
 */
public class HVACService extends SmartService {

	// Devices used in the HVAC service
	private TemperatureSensor temperatureSensor;
	private HumiditySensor humiditySensor;
	private HumanPresenceSensor humanPresenceSensor;
	private AirConditioner airConditioner;
	private Radiator radiator;
	private Circulator circulator;
	private Window window;
	private TreeMap<String, Action> actions = new TreeMap<String, Action>();

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

	public HVACService() {
	}

	public void prepareActions() {
		ActionFactory f = new ActionFactory();
		//Actions for AC
		actions.put("AC.off",  f.create("AC.off", airConditioner, "off", new Object[]{}, "Turn off AC."));
		actions.put("AC.on",   f.create("AC.on", airConditioner, "on", new Object[]{}, "Turn on AC."));
		//AC setTemperature
		actions.put("AC.setTemp22", f.create("AC.setTemp22", airConditioner, "setTemperature",
				new Object[]{new Integer(22)}, "Set AC temperature 22."));
		actions.put("AC.setTemp21", f.create("AC.setTemp21", airConditioner, "setTemperature",
				new Object[]{new Integer(21)}, "Set AC temperature 21."));
		actions.put("AC.setTemp20", f.create("AC.setTemp20", airConditioner, "setTemperature",
				new Object[]{new Integer(20)}, "Set AC temperature 20."));
		actions.put("AC.setTemp19", f.create("AC.setTemp19", airConditioner, "setTemperature",
				new Object[]{new Integer(19)}, "Set AC temperature 19."));
		//AC setSpeed
		actions.put("AC.setSpeed0", f.create("AC.setSpeed0", airConditioner, "setFanSpeed",
				new Object[]{new Integer(0)}, "Set AC speed 0."));
		actions.put("AC.setSpeed1", f.create("AC.setSpeed1", airConditioner, "setFanSpeed",
				new Object[]{new Integer(1)}, "Set AC speed 1."));
		actions.put("AC.setSpeed2", f.create("AC.setSpeed2", airConditioner, "setFanSpeed",
				new Object[]{new Integer(2)}, "Set AC speed 2."));

		//Actions for Radiator
		actions.put("Radiator.off",  f.create("Radiator.off", radiator, "off", new Object[]{}, "Turn off radiator."));
		actions.put("Radiator.on",   f.create("Radiator.on",  radiator, "on", new Object[]{}, "Turn on radiator."));
		//Radiator setTemperature
		actions.put("Radiator.setTemp22", f.create("Radiator.setTemp22", radiator, "setTemperature",
				new Object[]{new Integer(22)}, "Set radiator temperature 22."));
		actions.put("Radiator.setTemp20", f.create("Radiator.setTemp20", radiator, "setTemperature",
				new Object[]{new Integer(20)}, "Set radiator temperature 20."));

		//Actions for Circulator
		actions.put("Circulator.off", f.create("Circulator.off", circulator, "off", new Object[]{}, "Turn off circulator."));
		actions.put("Circulator.on", f.create("Circulator.on", circulator, "on", new Object[]{}, "Turn on circulator."));
		//Circulator setSpeed
		actions.put("Circulator.setSpeed0", f.create("Circulator.setSpeed0", circulator, "setSpeed",
				new Object[]{new Integer(0)}, "Set circulator speed 0."));
		actions.put("Circulator.setSpeed1", f.create("Circulator.setSpeed1", circulator, "setSpeed",
				new Object[]{new Integer(1)}, "Set circulator speed 1."));
		actions.put("Circulator.setSpeed2", f.create("Circulator.setSpeed2", circulator, "setSpeed",
				new Object[]{new Integer(2)}, "Set circulator speed 2."));

		//Actions for Window
		actions.put("Window.close", f.create("Window.close", window, "close", new Object[]{}, "Close Window."));
		actions.put("Window.open", f.create("Window.open", window, "open", new Object[]{}, "Open Window."));


	}


	@Override
	/**
	 * Observe the state by gathering status of all devices
	 */
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
	/**
	 * Interprete the state by calculating discomfort index
	 */
	public Context interpreteState(State state) {
		// Three kinds of contexts are evaluated: DI, discomfort, and peopleExist
		String [] keys = {"DI", "discomfort","peopleExist"};
		Context con = new Context(keys);

		//  (1) Deriving discomfort based on the discomfort index
		// Take the current temperature from the state
		double t = Double.parseDouble(state.getDeviceStatus(temperatureSensor.getId())
				.getValue("temperature"));

		// Take the current humidity from the state
		double h = Double.parseDouble(state.getDeviceStatus(humiditySensor.getId())
				.getValue("humidity"));

		// Calculate discomfort index
		// DI = 0.81*t + 0.01 * h * (0.99*t - 14.3) + 46.3;
		// ----------------------------------------------
		//    DI value         Condition
		// ----------------------------------------------
		//      DI <=55        Cold
		//  55< DI <=60        A bit cold
		//  60< DI <=65        It's OK
		//  65< DI <=70        Comfortable
		//  70< DI <=75        Not hot
		//  75< DI <=80        A bit hot
		//  80< DI <=85        Hot
		//  85< DI             Very hot. Call emergency
		// ----------------------------------------------
		double DI =  0.81*t + 0.01 * h * (0.99*t - 14.3) + 46.3;

		//set to the context
		con.setValue("DI", Double.toString(DI));

		//Derive the context discomfort
		String msg;
		if (DI<=55) {msg = "Cold"; }
		else if (DI <= 60) { msg = "A bit cold"; }
		else if (DI <= 65) { msg = "It's OK"; }
		else if (DI <= 70) { msg = "Comfortable"; }
		else if (DI <= 75) { msg = "Not hot"; }
		else if (DI <= 80) { msg = "A bit hot"; }
		else if (DI <= 85) { msg = "Hot"; }
		else               { msg = "Very hot. Call emergency"; }

		//set to the context
		con.setValue("discomfort", msg);

		// (2) Derive the context peopleExist

		// It can be directly derived from the human presence sensor
		String peopleExist = state.getDeviceStatus(humanPresenceSensor.getId()).getValue("present");

		//set to the context
		con.setValue("peopleExist", peopleExist);

		return con;
	}

	@Override
	/**
	 * Evaluate if the current context is acceptable for the service.
	 */
	public boolean isAcceptable(State state, Context context) {
		//HVAC service reaches an acceptable state iff
		// - (cond1) no device is working AND the room is comfortable
		//     OR
		// - (cond2) no device is working AND nobody is in the room
		boolean noDeviceWorking = state.getDeviceStatus(airConditioner.getId()).getValue("power").equals("false")
				&& state.getDeviceStatus(radiator.getId()).getValue("power").equals("false")
				&& state.getDeviceStatus(circulator.getId()).getValue("power").equals("false")
				&& state.getDeviceStatus(window.getId()).getValue("state").equals("close");
		boolean nobodyExist = context.getValue("peopleExist").equals("false");
		boolean comfortable = context.getValue("discomfort").equals("Comfortable");

		System.out.println(" - No device working: " + noDeviceWorking);
		System.out.println(" - Nobody exist: " + nobodyExist);
		System.out.println(" - The room is comfortable: " + comfortable + " (" + context.getValue("discomfort")+ ")");

		boolean cond = (noDeviceWorking && comfortable) || (noDeviceWorking && nobodyExist);
		System.out.println(" #HVAC reaches the acceptable state:" + cond);
		System.out.println();
		return cond;
	}

	@Override
	/**
	 * Propose a set of actions according to the level of discomfort index.
	 */
	public Action[] proposeActions(State state, Context context) {
		// Propose a plan depending on the level of the discomfort index
		// The proposal is made according to the following policy
		// ------------------------------------------------------------------------------
		// [Condition]                         [Proposed Actions]
        //                      AC         Radiator      Circulator      Window
		// ------------------------------------------------------------------------------
		// Cold                 off          on, temp=24     on, speed=1    close
        // A bit cold           off          on, temp=22     on, speed=0    close
		// It's OK              off          on, temp=22        off         close
		// Comfortable          off            off              off         close
		// Not hot       on, temp=22,speed=0   off              off         close
		// A bit hot     on, temp=21,speed=0   off           on, speed=0    close
		// Hot           on, temp=20,speed=1   off           on, speed=1    close
		// Emergency     on, temp=19,speed=2   off           on, speed=2    open
		// ------------------------------------------------------------------------------
		ArrayList<Action> list = new ArrayList<Action>();
		String discomfort = context.getValue("discomfort");
		switch (discomfort) {
		case "Cold":
			list.add(actions.get("AC.off"));
			list.add(actions.get("Radiator.on"));
			list.add(actions.get("Radiator.setTemp24"));
			list.add(actions.get("Circulator.on"));
			list.add(actions.get("Circulator.setSpeed1"));
			list.add(actions.get("Window.close"));
			break;
		case "A bit cold":
			list.add(actions.get("AC.off"));
			list.add(actions.get("Radiator.on"));
			list.add(actions.get("Radiator.setTemp22"));
			list.add(actions.get("Circulator.on"));
			list.add(actions.get("Circulator.setSpeed0"));
			list.add(actions.get("Window.close"));
			break;
		case "It's OK":
			list.add(actions.get("AC.off"));
			list.add(actions.get("Radiator.on"));
			list.add(actions.get("Radiator.setTemp22"));
			list.add(actions.get("Circulator.off"));
			list.add(actions.get("Window.close"));
			break;
		case "Comfortable":
			list.add(actions.get("AC.off"));
			list.add(actions.get("Radiator.off"));
			list.add(actions.get("Circulator.off"));
			list.add(actions.get("Window.close"));
			break;
		case "Not hot":
			list.add(actions.get("AC.on"));
			list.add(actions.get("AC.setTemp22"));
			list.add(actions.get("AC.setSpeed0"));
			list.add(actions.get("Radiator.off"));
			list.add(actions.get("Circulator.off"));
			list.add(actions.get("Window.close"));
			break;
		case "A bit hot":
			list.add(actions.get("AC.on"));
			list.add(actions.get("AC.setTemp21"));
			list.add(actions.get("AC.setSpeed0"));
			list.add(actions.get("Radiator.off"));
			list.add(actions.get("Circulator.on"));
			list.add(actions.get("Circulator.setSpeed0"));
			list.add(actions.get("Window.close"));
			break;
		case "Hot":
			list.add(actions.get("AC.on"));
			list.add(actions.get("AC.setTemp20"));
			list.add(actions.get("AC.setSpeed1"));
			list.add(actions.get("Radiator.off"));
			list.add(actions.get("Circulator.on"));
			list.add(actions.get("Circulator.setSpeed1"));
			list.add(actions.get("Window.close"));
			break;
		case "Very hot. Call emergency":
			list.add(actions.get("AC.on"));
			list.add(actions.get("AC.setTemp19"));
			list.add(actions.get("AC.setSpeed2"));
			list.add(actions.get("Radiator.off"));
			list.add(actions.get("Circulator.on"));
			list.add(actions.get("Circulator.setSpeed2"));
			list.add(actions.get("Window.open"));
			break;
		default:
		}
		for (Action a : list) {
			System.out.println("  -? Action " + a.getId() + " is proposed.");
		}
		System.out.println();
		return list.toArray(new Action[0]);
	}

	@Override
	/**
	 * Execute all or (some) actions proposed.
	 */
	public void executeActions(State state, Context context, Action[] actions) {
		//Currently execute all actions proposed...
		for (Action a: actions) {
			a.execute();
			System.out.println("  -! Action " + a.getId() + " is executed.");
		}
		System.out.println();
	}

}
