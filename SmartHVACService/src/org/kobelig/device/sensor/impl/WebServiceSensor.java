package org.kobelig.device.sensor.impl;

import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


/**
 * A client of SensorBoxService. Configuration is given by sensorbox.property.
 * @author masa-n
 *
 */
public class WebServiceSensor extends Sensor {
	protected String url;

	public WebServiceSensor(String id) {
		this.id = id;
		Properties prop = new Properties();

		try {
			prop.load(getClass().getResourceAsStream("/META-INF/device.properties"));
			url = prop.getProperty(id);
			//System.out.println("Sensor endpoint: " + url);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			Logger.getLogger(getClass()).error("Failed to initialize sensor service. Aborted.");
			e.printStackTrace();
		}
	}

	@Override
	public double getValue() {
		double value=0.0;

		// TODO 自動生成されたメソッド・スタブ
		try {
			DocumentBuilderFactory factory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Node root = builder.parse(url);
			String val = root.getFirstChild().getTextContent();
			value = Double.parseDouble(val);

		} catch (ParserConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
