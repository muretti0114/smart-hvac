package org.kobelig.device.appliance.impl;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.kobelig.device.type.Circulator;
import org.kobelig.device.type.DeviceOperationException;
import org.kobelig.state.DeviceStatus;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CS27CirculatorAdapter implements Circulator {
	private String baseurl;
	private String id;
	private String [] keys = {"id", "power", "speed", "direction"};
	private DeviceStatus status = new DeviceStatus(keys);

	public CS27CirculatorAdapter(String baseurl) {
		this.baseurl = baseurl;
		this.id = invokeWebService(baseurl+"/getId");
		status.setValue("id", id);
	}


	@Override
	public String getId() {
		// TODO 自動生成されたメソッド・スタブ
		return id;
	}

	@Override
	public DeviceStatus getStatus() {
		String url = baseurl + "/getStatus";

		try {
			DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Node root = builder.parse(url);
			NodeList personChildren = root.getFirstChild().getFirstChild().getChildNodes();
			for (int i=0; i< personChildren.getLength(); i++) {
				Node n = personChildren.item(i);
				status.setValue(n.getNodeName(), n.getTextContent());
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


		return status;
	}
	@Override
	public void on() throws DeviceOperationException {
		String res = invokeWebService(baseurl+"/on");
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}
	}

	@Override
	public void off() throws DeviceOperationException  {
		String res = invokeWebService(baseurl+"/off");
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}
	}

	@Override
	public void setDirection(int dir) throws DeviceOperationException {
		String res = invokeWebService(baseurl+"/setDirection?direction" + dir);
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}
	}

	@Override
	public void setSpeed(int speed) throws DeviceOperationException {
		String res = invokeWebService(baseurl+"/setSpeed?direction" + speed);
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}
	}
	private String invokeWebService(String url) {
		String value = "";

		try {
			DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Node root = builder.parse(url);
			value = root.getFirstChild().getTextContent();
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
}
