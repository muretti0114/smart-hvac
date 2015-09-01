package org.kobelig.state;


public interface Status {
	public void reset();
	
/*	public void loadStatus() throws Exception{
		File file = new File(fileName);
		if(file.canRead()){			
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(fileName));
			status = (FANStatus)oin.readObject();
			oin.close();
		}
		else
			resetStatus();
	}
	
	public void saveStatus() throws Exception{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(status);
		out.flush();
		out.close();
	}
	
	public void resetStatus(){
		status.setPower(false);
		status.setSwing(false);
		status.setAirVolume(1);
		status.setTimer(0);
		
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(status);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.getLogger(getClass()).info("Status Reset");
	}
*/
}