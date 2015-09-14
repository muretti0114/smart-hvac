package org.kobelig.device.appliance.impl;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.kobelig.device.type.AirConditioner;
import org.kobelig.device.type.DeviceOperationException;
import org.kobelig.state.DeviceStatus;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CS27AirConditionerAdapter implements AirConditioner {
	private String baseurl;
	private String id;
	private String [] keys = {"id", "power", "temperature", "speed"};
	private DeviceStatus status = new DeviceStatus(keys);

	public CS27AirConditionerAdapter(String baseurl) {
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
			NodeList list = root.getFirstChild().getFirstChild().getChildNodes();
			for (int i=0; i< list.getLength(); i++) {
				Node n = list.item(i);
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
	public void off() throws DeviceOperationException {
		String res = invokeWebService(baseurl+"/off");
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}
	}

	@Override
	public void setTemperature(double temp) throws DeviceOperationException  {
		int t = (int) temp;
		String res = invokeWebService(baseurl+"/setTemperature?temperature="+t);
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}
	}

	@Override
	public void setFanSpeed(int level) throws DeviceOperationException {
		String res = invokeWebService(baseurl+"/setSpeed?speed="+level);
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
