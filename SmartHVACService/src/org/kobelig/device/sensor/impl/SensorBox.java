package org.kobelig.device.sensor.impl;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * A client of SensorBoxService
 * @author masa-n
 *
 */
public class SensorBox {
	private String url; //End point of the SensorBoxService

	public SensorBox(String url) {
		setUrl(url);
	}

	/**
	 * Get the value of the sensor specified by the id.
	 * @param id Sensor id in the sensor box.
	 * @return Value of the sensor.
	 */
	public double getValue(String id) {
		double value=0.0;

		String endpoint = url + "/getValue?id=" + id;

		// TODO 自動生成されたメソッド・スタブ
		try {
			DocumentBuilderFactory factory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Node root = builder.parse(endpoint);
			String val = root.getFirstChild().getTextContent();

			//Numeric conversion for boolean  true -> 1, false -> 0
			if (val.equals("true")) {
				val = "1.0";
			} else if (val.equals("false")) {
				val = "0.0";
			}

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

	/**
	 * Get the attribute information of a sensor
	 *
	 * @param id    id of the sensor
	 * @param attr  name of attribute
	 * @return attribute
	 */
	public String getInfo(String id, String attr) {
		String info = "";
		String endpoint = url + "/getInfo?id=" + id;

		// TODO 自動生成されたメソッド・スタブ
		try {
			DocumentBuilderFactory factory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Node root = builder.parse(endpoint);

			NodeList list = root.getFirstChild().getFirstChild().getChildNodes();
			for (int i=0; i< list.getLength(); i++) {
				Node n = list.item(i);
				if (attr.equals(n.getNodeName())) {
					info = n.getTextContent();
				}
			}
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

		return info;
	}





	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
