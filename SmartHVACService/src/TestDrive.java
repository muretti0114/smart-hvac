import org.kobelig.device.appliance.impl.CS27AirConditionerAdapter;
import org.kobelig.device.appliance.impl.CS27CirculatorAdapter;
import org.kobelig.device.appliance.impl.CS27RadiatorAdapter;
import org.kobelig.device.appliance.impl.CS27WindowAdapter;
import org.kobelig.device.sensor.impl.PresenceSensorServiceAdapter;
import org.kobelig.device.sensor.impl.SBSHumidityAdapter;
import org.kobelig.device.sensor.impl.SBSTemperatureAdapter;
import org.kobelig.device.type.AirConditioner;
import org.kobelig.device.type.Circulator;
import org.kobelig.device.type.HumanPresenceSensor;
import org.kobelig.device.type.HumiditySensor;
import org.kobelig.device.type.Radiator;
import org.kobelig.device.type.TemperatureSensor;
import org.kobelig.device.type.Window;
import org.kobelig.service.HVACService;

public class TestDrive {

	public static void main(String[] args) throws Exception {
		System.out.println("Hello HVAC Service!!");

		// Instantiate all necessary components
		HumanPresenceSensor presenceSensor = new PresenceSensorServiceAdapter(
				"http://129.88.49.247:8080/axis2/services/PresenceSensorService"
				);

		TemperatureSensor tempSensor = new SBSTemperatureAdapter(
				"http://129.88.49.247:8080/axis2/services/SensorBoxService",
				"temperature");

		HumiditySensor humidSensor = new SBSHumidityAdapter(
				"http://129.88.49.247:8080/axis2/services/SensorBoxService",
				"humidity");


		AirConditioner ac = new CS27AirConditionerAdapter(
				"http://129.88.49.247:8080/axis2/services/SimpleAirconService"
				);

		Window w = new CS27WindowAdapter(
				"http://129.88.49.247:8080/axis2/services/SimpleWindowService"
				);

		Radiator r = new CS27RadiatorAdapter(
				"http://129.88.49.247:8080/axis2/services/SimpleRadiatorService"
				);

		Circulator c = new CS27CirculatorAdapter(
				"http://129.88.49.247:8080/axis2/services/SimpleCirculatorService"
				);


		// Create HVAC service
		HVACService s = new HVACService();
		s.setWaitTime(10*1000);

		// Bind the components with the HVAC service
		s.setAirConditioner(ac);
		s.setRadiator(r);
		s.setWindow(w);
		s.setCirculator(c);
		s.setTemperatureSensor(tempSensor);
		s.setHumiditySensor(humidSensor);
		s.setHumanPresenceSensor(presenceSensor);

		// Start the service
		s.startService();
	}

}
