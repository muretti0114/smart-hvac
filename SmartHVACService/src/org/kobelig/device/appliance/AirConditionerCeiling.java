package org.kobelig.device.appliance;

import org.apache.log4j.Logger;
import org.kobelig.com.acd.ACDExecException;
import org.kobelig.com.acd.ACDproxy;
import org.kobelig.state.AirConditionerCeilingStatus;


/**
 * AirConditioner class for the ceiling air conditioner DAIKIN
 * @author  Takuya Inada
 * @author  Masahide Nakamura 2013-08-05
 * @author  Masahide Nakamura 2015-05-25 The timer features are deleted.
 */
public class AirConditionerCeiling extends Appliance {
	ACDproxy aircon = new ACDproxy();


	AirConditionerCeilingStatus status = new AirConditionerCeilingStatus();
	private final int SIGNAL_WAITTIME = 0;


	/**
	 * Drive mode parameters
	 */
	private static final int COLDING = 1;
	//	private static final int AUTO = 2;
	private static final int WIND = 3;
	private static final int DRY = 4;
	private static final int HEATING = 5;

	/**
	 * Create an air conditioner ceiling object with ID.
	 *
	 * @param id Identifier of the air conditioner
	 */
	public AirConditionerCeiling(String id)  {
		super();
		setId(id);
		fileName = getFileName();
		//Loading the persisted state if exists
		status = (AirConditionerCeilingStatus) super.loadStatus(status, fileName);
	}

	/**
	 * Get the current status
	 * @return status
	 */
	public AirConditionerCeilingStatus getStatus(){
		return this.status;
	}

	/**
	 * Just start the air conditioner, without retrieving the saved state.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean start() {
		boolean cond = false;

		try {
			aircon.exec(id, "on", SIGNAL_WAITTIME);

			status.setPower(true);
			saveStatus(status, fileName);

			Logger.getLogger(getClass()).info("OK " + id);

			//Success !!
			cond = true;
		} catch (ACDExecException e) {
			Logger.getLogger(getClass()).error("Device control error. " + id);
			e.printStackTrace();
		}

		return cond;
	}

	/**
	 * Turn on the air conditioner, and retrieve the previous drive mode.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean on()  {
		boolean cond = false;
		String nowMode = status.getOperationalStatus();

		try {
			aircon.exec(id, "on", SIGNAL_WAITTIME);

			status.setPower(true);
			saveStatus(status, fileName);

			Logger.getLogger(getClass()).info("OK " + id);

			//Success !!
			cond = true;
		} catch (ACDExecException e) {
			Logger.getLogger(getClass()).error("Device control error. " + id);
			e.printStackTrace();
		}

		if(nowMode != null)
			if(nowMode.equals("colding")){
				return colding(status.getTemperature(), status.getWindLevel(), 0);
			}
			else if(nowMode.equals("sendWind")){
				return sendWind(status.getWindLevel(), 0);
			}
			else if(nowMode.equals("dry")){
				return dry(status.getTemperature());
			}
			else if(nowMode.equals("heating")){
				return heating(status.getTemperature(), status.getWindLevel(), 0);
			}
			else{
				Logger.getLogger(getClass()).error(nowMode + ": Operational mode out of range. " + id);
				return false;
			}


		return cond;
	}
	/**
	 * Turn off the air conditioner.
	 *
	 * @return true if the operation is succeeded.
	 */

	public boolean off()  {
		boolean cond = false;

		try {
			aircon.exec(id, "off", SIGNAL_WAITTIME);

			status.setPower(false);
			status.printStatus();
			saveStatus(status, fileName);

			Logger.getLogger(getClass()).info("OK " + id);

			cond = true;
		} catch (ACDExecException e) {
			Logger.getLogger(getClass()).error("Device control error. " + id);
			e.printStackTrace();
		}

		return cond;
	}

	/**
	 * Activate the heating mode.
	 * @param temp Specify the temperature setting. The value of 23, 25, 27 or 29 is only accepted.
	 *             Another value is adjusted the nearest accepted value.
	 * @param windLevel  Specify the wind level. The value 1 is for strong, other is for weak.
	 * @param windDirection Specify the wind direction. The value 1 is for swing, other is for normal.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean heating(int temp, int windLevel, int windDirection)  {
		boolean cond = false;
		String cmd = "";      //command to send

		//Adjust the given temp to the nearest setting.
		if (temp<23) temp = 23;
		else if (temp>29) temp = 29;
		else if (temp%2==0) {
			temp--;
		}

		//The temp setting is restricted to 23, 25, 27 or 29.
		switch (temp) {
		case 23:
		case 25:
		case 27:
		case 29:
			String swing = (windDirection==1)?"swing_": "";
			String level = (windLevel==1)? "strong" : "weak";

			//暖房コマンド名は，hot_strong_25   hot_weak_swing_29 のような感じ．
			cmd  = String.format("hot_%s_%s%2d", level, swing, temp);
			break;
		default:
			Logger.getLogger(getClass()).info("temp = " + temp+": Temperature is out of range.");
			return false;
		}

		//Sending the command
		try {
			aircon.exec(id, cmd, SIGNAL_WAITTIME);
			Logger.getLogger(getClass()).info("Sending cmd " + cmd);

			//Setting the status
			status.setOperationalStatus("heating");
			status.setWindLevel(windLevel);
			status.setWindDirection(windDirection);
			status.setTemperature(temp);
			status.printStatus();
			saveStatus(status, fileName);

			Logger.getLogger(getClass()).info("OK " + id);

			cond = true;
		} catch (ACDExecException e) {
			Logger.getLogger(getClass()).error("Device control error." + id);
			e.printStackTrace();
		}
		return cond;
	}


	/**
	 * Activate the wind mode (without heating or colding).
	 *
	 * @param windLevel  Specify the wind level. The value 1 is for strong, other is for weak.
	 * @param windDirection Specify the wind direction. The value 1 is for swing, other is for normal.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean sendWind(int windLevel, int windDirection)  {
		boolean cond = false;

		//風量強フラグがたってればstrong, それ以外 weak
		String level = (windLevel==1)? "strong": "weak";

		//風向フラグがたってれば，_swingをつける．
		String swing = (windDirection==1)? "_swing": "";

		//暖房コマンド名は，wind_strong   wind_weak_swing のような感じ．
		String cmd  = String.format("wind_%s%s", level, swing);

		try {
			//コマンド送信
			aircon.exec(id, cmd, SIGNAL_WAITTIME);

			//状態セーブ
			status.setOperationalStatus("sendWind");
			status.setWindLevel(windLevel);
			status.setWindDirection(windDirection);
			status.printStatus();
			saveStatus(status, fileName);

			Logger.getLogger(getClass()).info("OK " + id);

			cond = true;
		} catch (ACDExecException e) {
			Logger.getLogger(getClass()).error("Device control error." + id);
			e.printStackTrace();
		}

		return cond;
	}

	/**
	 * Activate the colding mode.
	 *
	 * @param temp Specify the temperature setting. The value of 21, 23, 25 or 27 is only accepted.
	 *             Another value is adjusted the nearest accepted value.
	 * @param windLevel  Specify the wind level. The value 1 is for strong, other is for weak.
	 * @param windDirection Specify the wind direction. The value 1 is for swing, other is for normal.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean colding(int temp, int windLevel, int windDirection)  {
		boolean cond = false;
		String cmd = "";      //command to send

		//Adjust the given temp to the nearest setting.
		if (temp<21) temp = 21;
		else if (temp>27) temp = 27;
		else if (temp%2==0) {
			temp++;
		}

		//The temp setting is restricted to 21, 23, 25 or 27.
		switch (temp) {
		case 21:
		case 23:
		case 25:
		case 27:
			String swing = (windDirection==1)?"swing_" : "";
			String level = (windLevel==1) ? "strong": "weak";

			//The command is represented like cool_strong_25  or  cool_weak_swing_27
			cmd  = String.format("cool_%s_%s%2d", level, swing, temp);
			break;
		default:
			Logger.getLogger(getClass()).info("temp = " + temp+": Temperature is out of range.");
			return false;
		}

		//Sending the command
		try {
			aircon.exec(id, cmd, SIGNAL_WAITTIME);
			Logger.getLogger(getClass()).info("Sending cmd " + cmd);

			//Setting the status
			status.setOperationalStatus("colding");
			status.setWindLevel(windLevel);
			status.setWindDirection(windDirection);
			status.setTemperature(temp);
			status.printStatus();
			saveStatus(status, fileName);

			Logger.getLogger(getClass()).info("OK " + id);

			//Success!!
			cond = true;
		} catch (ACDExecException e) {
			Logger.getLogger(getClass()).error("Device control error. " + id);
			e.printStackTrace();
		}

		return cond;
	}

	/**
	 * Activate the dry mode. Not yet implemented because the IR signal was not correctly memorized.
	 */
	public boolean dry(int temp) {
		return false;
	}


	/**
	 * Change the wind level, which is either weak (0) or strong (1)
	 *
	 * @param windLevel - 0 for weak, 1 for strong
	 * @return true if the operation is succeeded.
	 */
	public boolean changeWindLevel(int windLevel)  {
		String nowMode = status.getOperationalStatus();

		//Perform the current operation mode with the given wind level
		if(nowMode.equals("colding")){
			return colding( status.getTemperature(), windLevel ,status.getWindDirection());
		}
		else if(nowMode.equals("heating")){
			return heating( status.getTemperature(), windLevel, status.getWindDirection());
		}
		else if(nowMode.equals("sendWind")){
			return sendWind(windLevel, status.getWindDirection());
		}
		else if(nowMode.equals("auto")){
			return auto();
		}
		else{
			Logger.getLogger(getClass()).error(nowMode + ": Operational mode out of range. " + id);
			return false;
		}
	}

	/**
	 * Change the wind level to weak.
	 *
	 * @return true if the operation is succeeded. false if the level is already weak.
	 */
	public boolean downWindLevel()  {
		if(status.getWindLevel() == 1)
			return this.changeWindLevel(0);
		return false;
	}

	/**
	 * Change the wind level to strong.
	 *
	 * @return true if the operation is succeeded. false if the level is already strong.
	 */
	public boolean upWindLevel()  {
		if(status.getWindLevel() == 0)
			return this.changeWindLevel(1);
		return false;
	}

	/**
	 * Change the temperature setting in the current operation mode.
	 * @param temp Specify the temperature degree to change.
	 * @return true if the operation is succeeded.
	 */
	public boolean changeTemperature(int temp)  {
		String nowMode = status.getOperationalStatus();

		//Perform the current operation mode with the given temperature degree
		if(nowMode.equals("colding")){
			return colding(temp, status.getWindLevel(), status.getWindDirection());
		}
		else if(nowMode.equals("sendWind")){
			return sendWind(status.getWindLevel(), status.getWindDirection());
		}
		else if(nowMode.equals("dry")){
			return dry(temp);
		}
		else if(nowMode.equals("heating")){
			return heating(temp, status.getWindLevel(), status.getWindDirection());
		}
		else{
			Logger.getLogger(getClass()).error(nowMode + ": Operational mode out of range. " + id);
			return false;
		}
	}

	/**
	 * Decrease the temperature setting by 2 degree.
	 *
	 * @return true if the operation is succeeded.
	 */

	public boolean downTemperature()  {
		return changeTemperature(status.getTemperature()-2);
	}

	/**
	 * Increase the temperature setting by 2 degree.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean upTemperature()  {
		return changeTemperature(status.getTemperature()+2);
	}

	/**
	 * Change the operational mode to colding (1), wind (3), dry (4) or heating (5).
	 *
	 * @param mode a number representing colding (1), wind (3), dry (4) or heating (5).
	 * @return true if the operation is succeeded.
	 */
	public boolean changeMode(int mode)  {
		if(mode == COLDING){
			return colding(status.getTemperature(), status.getWindLevel(), status.getWindDirection());
		}
		else if(mode == WIND){
			return sendWind(status.getWindLevel(), status.getWindDirection());
		}
		else if(mode == DRY){
			return dry(status.getTemperature());
		}
		else if(mode == HEATING){
			return heating(status.getTemperature(), status.getWindLevel(), status.getWindDirection());
		}
		else{
			Logger.getLogger(getClass()).error(mode + ": Operational mode out of range. " + id);
			return false;
		}
	}

	/**
	 * Change the operational mode to "colding", "sendWind", "dry", or "heating"
	 *
	 * @param mode a string representing "colding", "sendWind", "dry", or "heating"
	 * @return true if the operation is succeeded.
	 */
	public boolean changeMode(String mode)  {

		if(mode.equalsIgnoreCase("colding")){
			return colding(status.getTemperature(), status.getWindLevel(), status.getWindDirection());
		}
		else if(mode.equalsIgnoreCase("sendWind")){
			return sendWind(status.getWindLevel(), status.getWindDirection());
		}
		else if(mode.equalsIgnoreCase("dry")){
			return dry(status.getTemperature());
		}
		else if(mode.equalsIgnoreCase("heating")){
			return heating(status.getTemperature(), status.getWindLevel(), status.getWindDirection());
		}
		else{
			Logger.getLogger(getClass()).error(mode + ": Operational mode out of range. " + id);
			return false;
		}
	}
	/**
	 * Activate the automatic operation. Currently it does not work.
	 * @return
	 * @throws Exception
	 */
	public boolean auto()  {
		/*
		start();
		aircon.sendSignal(DIAL_NUM, 20, SIGNAL_SENDTIME);

		status.setOperationalStatus("auto");
		status.setWindLevel(2);
		status.setTemperature(23);
		Logger.getLogger(getClass()).info("OK " + id);
		saveStatus(status, fileName);
		Thread.sleep(700);
		return true;
		 */
		return false;

	}

	/**
	 * Change the operational mode to "colding", "sendWind", "dry", or "heating"
	 *
	 * @param operation a string representing "colding", "sendWind", "dry", or "heating"
	 * @return true if the operation is succeeded.
	 */
	public boolean changeOparation(String operation)  {
		if(operation.equalsIgnoreCase("colding")){
			return colding(status.getTemperature(), status.getWindLevel(), status.getWindDirection());
		}
		else if(operation.equalsIgnoreCase("auto")){
			return auto();
		}
		else if(operation.equalsIgnoreCase("sendWind")){
			return sendWind(status.getWindLevel(), status.getWindDirection());
		}
		else if(operation.equalsIgnoreCase("dry")){
			return dry(status.getTemperature());
		}
		else if(operation.equalsIgnoreCase("heating")){
			return heating(status.getTemperature(), status.getWindLevel(), status.getWindDirection());
		}
		else{
			Logger.getLogger(getClass()).error(operation + ": Operational mode out of range. " + id);
			return false;
		}
	}

	/**
	 * Change the wind direction to fixed (0) or swing (1)
	 *
	 * @param windDirection - Specify 0 for fixed, 1 for swing
	 * @return true if the operation is succeeded.
	 */
	public boolean changeWindDirection(int windDirection)  {
		if(status.getOperationalStatus().equalsIgnoreCase("colding")){
			return colding(status.getTemperature(), status.getWindLevel(), windDirection);
		}
		else if(status.getOperationalStatus().equalsIgnoreCase("auto")){
			return auto();
		}
		else if(status.getOperationalStatus().equalsIgnoreCase("sendWind")){
			return sendWind(status.getWindLevel(), windDirection);
		}
		else if(status.getOperationalStatus().equalsIgnoreCase("dry")){
			return dry(status.getTemperature());
		}
		else if(status.getOperationalStatus().equalsIgnoreCase("heating")){
			return heating(status.getTemperature(), status.getWindLevel(), windDirection);
		}
		else{
			return false;
		}
	}

	/**
	 * Set the wind direction to swing.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean startWindDirection() {
		return changeWindDirection(1);
	}

	/**
	 * Set the wind direction to fixed.
	 *
	 * @return true if the operation is succeeded.
	 */
	public boolean stopWindDirection()  {
		return changeWindDirection(0);
	}


	/**
	 * main for testing purpose
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception{
		AirConditionerCeiling ac = new AirConditionerCeiling("aircon001");
		ac.on();
		ac.colding(25, 0, 1);
		ac.off();

	}

}