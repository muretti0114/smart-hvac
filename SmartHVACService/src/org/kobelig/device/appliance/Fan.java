package org.kobelig.device.appliance;


import org.apache.log4j.Logger;
import org.kobelig.com.acd.ACDproxy;
import org.kobelig.state.FanStatus;

/**
 * @author  Hiroshi Igaki
 * @author  Masahide Nakamura   2013-07-08
 */
public class Fan extends Appliance{

	FanStatus status = new FanStatus();
	String fileName;
	//private Appliance ap = null;

	private final int SIGNAL_WAITTIME = 0;

	private ACDproxy fan = new ACDproxy();


	/**
	 * Create a fan object with ID.
	 *
	 * @param id Identifier of the fan
	 */
	public Fan(String id) {
		super();
		setId(id);
		fileName = getFileName();
		//Loading the persisted state if exists
		status = (FanStatus) super.loadStatus(status, fileName);

	}
	/**
	 * @return  status
	 * @uml.property  name="status"
	 */
	public FanStatus getStatus(){
		return this.status;
	}


	public boolean on() throws Exception {
		if (status.getPower()==false) {
			System.out.println("fan's sendsignal begins");
			fan.exec(id, "on", SIGNAL_WAITTIME);
			status.setPower(true);
			Logger.getLogger(getClass()).info("OK");
			super.saveStatus(status,fileName);
			return true;
		} else {
			Logger.getLogger(getClass()).info("Already On.");
			return false;
		}
	}

	public boolean off() throws Exception {
		if (status.getPower()==true) {

			fan.exec(id, "off", SIGNAL_WAITTIME);
			status.setPower(false);
			Logger.getLogger(getClass()).info("OK");
			super.saveStatus(status,fileName);
			status=(FanStatus)super.resetStatus(status,fileName);
			return true;
		} else {
			Logger.getLogger(getClass()).info("Already Off.");
			return false;
		}
	}

	public void air_volume(int vol) throws Exception{
		Thread.sleep(SIGNAL_WAITTIME);
		if(0<vol && vol<4){
			if(status.getAirVolume() < vol){
				for(int i=0 ; i < vol - status.getAirVolume() ; i++){
					fan.exec(id, "on", SIGNAL_WAITTIME);
				}
			}else if(status.getAirVolume() > vol){
				for(int i=0 ; i < vol + 3 - status.getAirVolume() ; i++){
					fan.exec(id, "on", SIGNAL_WAITTIME);
				}
			}
			status.setAirVolume(vol);
			Logger.getLogger(getClass()).info("OK");
			super.saveStatus(status,fileName);
		}else
			Logger.getLogger(getClass()).info("OK");
		super.saveStatus(status,fileName);
	}


	public void swing() throws Exception{
		System.out.println("swing start");
		fan.exec(id, "swing", SIGNAL_WAITTIME);
		if(status.getSwing())
			status.setSwing(false);
		else
			status.setSwing(true);
		Logger.getLogger(getClass()).info("OK");
		super.saveStatus(status,fileName);
	}

	public void timer(int time) throws Exception{
		int param_num = 0;
		int status_num =0;
		on();
		Thread.sleep(SIGNAL_WAITTIME);
		if(time==0 || time ==1 || time ==2 || time ==4 || time ==8){
			switch(time){
			case 0:
				param_num = 0;
				break;
			case 1:
				param_num = 1;
				break;
			case 2:
				param_num = 2;
				break;
			case 4:
				param_num = 3;
				break;
			case 8:
				param_num = 4;
				break;
			}
			switch(status.getTimer()){
			case 0:
				status_num = 0;
				break;
			case 1:
				status_num = 1;
				break;
			case 2:
				status_num = 2;
				break;
			case 4:
				status_num = 3;
				break;
			case 8:
				status_num = 4;
				break;
			}
			System.out.println(status_num);
			System.out.println(param_num);
			if(status_num < param_num){
				for(int i=0 ; i < param_num - status_num ; i++){
					fan.exec(id, "timer", SIGNAL_WAITTIME);
				}
			}else if(status_num > param_num){
				for(int i=0 ; i < param_num + 5 - status_num ; i++){
					fan.exec(id, "timer", SIGNAL_WAITTIME);
				}
			}
			status.setTimer(time);
			Logger.getLogger(getClass()).info("TimerSetOK");
			super.saveStatus(status,fileName);
		}else
			Logger.getLogger(getClass()).info("Not exist such Timer");
	}
}
