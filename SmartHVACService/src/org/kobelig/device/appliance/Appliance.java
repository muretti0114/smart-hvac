package org.kobelig.device.appliance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.kobelig.state.Status;

/**
 * Abstract appliance,
import org.kobelig.state.Status; consisting of an object ID, and persistence mechanism of the object state.
 *
 * @author  Hiroshi Igaki
 * @author Matsuo Shuhei(saveOperationLog)
 * @author Masahide Nakamura  2015-05-25
 */
public class Appliance{
	/** Unique Id of the appliance */
	protected String id;
	/** Filename of the state file */
	protected String fileName;
	/** Directory to store the state file */
	private static final String STATEDIR="logs/CS27HNS/state/";

	public Appliance(){
		DOMConfigurator.configure(getClass().getClassLoader().getResource("META-INF/log4j-CS27HNS.xml"));
		//Create a directory for the state file if not exist.
		File dir = new File (STATEDIR);
		if (dir.exists()==false) {
			dir.mkdirs();
		}
	}

	/**
	 * Set the appliance id.
	 * @param id - Appliance Id to set
	 */

	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Get the appliance id.
	 * @return Appliance id obtained.
	 */
	public String getId() {
		return id;
	}


	/**
	 * Load the object status from the file.
	 *
	 * @param status   pointer of the appliance status
	 * @param fileName filename to which the status is persisted.
	 * @return the new status object loaded, or the previous status if failed.
	 */
	public Status loadStatus(Status status, String fileName) {

		String filePath = STATEDIR + fileName;

		try {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(filePath));
			status = (Status)oin.readObject();
			oin.close();
			Logger.getLogger(getClass()).debug("Status loaded.");
		} catch (FileNotFoundException e) {
			Logger.getLogger(getClass()).error("Status file cannot be found.");
			e.printStackTrace();
			Logger.getLogger(getClass()).debug("Try to create a new file...");
			status = resetStatus(status, fileName);
		} catch (ClassNotFoundException e) {
			Logger.getLogger(getClass()).error("Status object cannot be restored.");
			e.printStackTrace();
			Logger.getLogger(getClass()).debug("Try to create a new file...");
			status = resetStatus(status, fileName);
		} catch (IOException e) {
			Logger.getLogger(getClass()).error("Status file cannot be loaded correctly.");
			e.printStackTrace();
			Logger.getLogger(getClass()).debug("Try to reset the status...");
			status = resetStatus(status, fileName);
		}

		return status;
	}

	/**
	 * Save the object status to the file.
	 *
	 * @param status   pointer of the appliance status
	 * @param fileName filename to which the status is persisted.
	 */
	public void saveStatus(Status status, String fileName) {
		String filePath = STATEDIR + fileName;

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(status);
			out.flush();
			out.close();
			Logger.getLogger(getClass()).debug("Status saved.");

		} catch (FileNotFoundException e) {
			Logger.getLogger(getClass()).error("Status file cannot be found. ");
			e.printStackTrace();
		} catch (IOException e) {
			Logger.getLogger(getClass()).error("Status file cannot be saved correctly.");
			e.printStackTrace();
		}
	}

	/**
	 * Reset the object status to the default and save to the file.
	 *
	 * @param status   pointer of the appliance status
	 * @param fileName filename to which the status is persisted.
	 * @return the new status object loaded, or the previous status if failed.
	 */
	public Status resetStatus(Status status, String fileName){
		String filePath = STATEDIR + fileName;

		status.reset();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(status);
			out.flush();
			out.close();
			Logger.getLogger(getClass()).debug("Status reset");

		} catch (FileNotFoundException e) {
			Logger.getLogger(getClass()).error("Status file cannot be found. ");
			e.printStackTrace();
		} catch (IOException e) {
			Logger.getLogger(getClass()).error("Status file cannot be saved correctly.");
			e.printStackTrace();
		}

		return status;
	}

	String getFileName() {
		String fileName = id + "@" + getClass().getName() + ".ini";

		return fileName;

	}

}