package org.kobelig.environment;


/**
 * @author Ben Yan, mars - june 2007
 *
 * july 2007, lydie : uniformisation of methods, JML specification
 */
public class HomeEnvironment {

	private /*@  spec_public  @*/ int consumption=0;
	private /*@  spec_public  @*/ int voltage=110;
	private /*@  spec_public  @*/ int frequency=0;
	private /*@  spec_public  @*/ int time=0;
	private /*@  spec_public  @*/ int temperature=0;
	private /*@  spec_public  @*/ int humidity=0;
	private /*@  spec_public  @*/ int volume=0;
	private /*@  spec_public  @*/ int brightness=0;
	private /*@  spec_public  @*/ int magnetic=0;
	private /*@  spec_public  @*/ int smoke=0;

    //Env1 : total power consumption should not exceed 10
	// @ public invariant (getConsumption() <= 30);

	//@ public invariant volume >=0;
	//@ public invariant (time>=0) &&  (time <24);
	public HomeEnvironment(){
		consumption=0;
		voltage=110;
		frequency=0;
		time=0;
		temperature=0;
		humidity=0;
		volume=0;
		brightness=0;
		magnetic=0;
		smoke=0;
	}

	public /*@ pure @*/ int getConsumption(){
    	//System.out.println("CurrentStateCommon'Consumption is " + consumption);
    	return consumption;
    }

    public /*@ pure @*/ int getTime(){
      	//System.out.println("Time is " + time);
    	return time;
    }
    public /*@ pure @*/  int getVoltage(){
    	//System.out.println("CurrentStateCommon'voltage is " + voltage);
    	return voltage;
    }

    public /*@ pure @*/ int getFrequency(){
    	//System.out.println("CurrentStateCommon'frequency is " + frequency);
    	return frequency;
    }

    public /*@ pure @*/ int getTemperature(){
    	//System.out.println("CurrentStateCommon'applianceInternalTemperature is " + temperature);
    	return temperature;
    }

    public /*@ pure @*/ int getHumidity(){
   		//System.out.println("CurrentStateCommon'humidity is " + humidity);
    	return humidity;
    }

    public /*@ pure @*/ int getSound(){
		return getVolume();
	}

	public /*@ pure @*/ int getVolume(){
    	//System.out.println("CurrentStateCommon'volume is " + volume);
    	return volume;
    }

    public /*@ pure @*/ int getBrightness(){
    	//System.out.println("CurrentStateCommon'applianceCurrentBrightness is " + brightness);
    	return brightness;
    }

    public /*@ pure @*/ int getMagnetic(){
    	//System.out.println("CurrentStateCommon'magnetic is " + magnetic);
    	return magnetic;
    }

    public /*@ pure @*/ int getSmoke(){
    	//System.out.println("CurrentStateCommon'smoke is " + smoke);
    	return smoke;
    }

    //@ requires (hum >=0) && (hum<=100) ;
    //@ requires (sound >=0);
	public void updateSomeEnvirnmentValues(int cons, int tmp, int hum, int sound, int bri){
		updateConsumption(cons);
		updateTemperature(tmp);
		updateHumidity(hum);
		updateVolume(sound);
		updateBrightness(bri);
	}


	public void updateConsumption(int cons){ consumption = consumption + cons; }
	public void updateTemperature(int t) { temperature = temperature+t;}

	//@ requires (hum >=0) && (hum<=100) ;
    public void updateHumidity(int hum) { humidity = hum;}


    public void updateVolume(int so) {if (so >= 0)  {volume = so;} }
    public void updateBrightness(int br) {brightness = brightness + br; }
    public void updateSmoke(int sm) {smoke = smoke+sm; }


    //
    public void updateHour() {
    	int t;
    	t = time;
    	t = (t +1)%24;
    	time = t;
    }

    //@ requires cons >=0;
	public void setConsumption(int cons){ consumption = cons; }
	public void setTemperature(int t) { temperature = t;}

	//@ requires (hum>=0) && (hum<=100);
    public void setHumidity(int hum) { humidity = hum;}

    //@ requires (so >=0);
    public void setVolume(int so) {volume = so; }
    public void setBrightness(int br) {brightness = br; }
    public void setSmoke(int sm) {smoke = sm; }

    //@ requires (t>=0);
    public void setHour(int t){ time = t%24;}

}

