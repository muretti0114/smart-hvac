package org.kobelig.device.sensor.impl;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.kobelig.device.type.HumanPresenceSensor;
import org.kobelig.state.DeviceStatus;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class PresenceSensorServiceAdapter implements HumanPresenceSensor {
	private String id;
	private String baseurl;

	public PresenceSensorServiceAdapter(String baseurl) {
		this.baseurl = baseurl;
		this.id = invokeWebService(baseurl+"/getId");
	}

	@Override
	public String getId() {
		// TODO 自動生成されたメソッド・スタブ
		return id;
	}

	@Override
	public DeviceStatus getStatus() {
		String [] keys = {"id", "present", "lastUpdateAt", "likelihood"};
		DeviceStatus s = new DeviceStatus(keys);
		s.setValue("id", id);
		s.setValue("present", invokeWebService(baseurl+"/isPresent"));
		s.setValue("lastUpdateAt", invokeWebService(baseurl+"/getLastUpdateAt"));
		s.setValue("likelihood", invokeWebService(baseurl+"/getLikelihood"));

		return s;
	}

	@Override
	public boolean isHumanPresent() {
		String result = invokeWebService(baseurl+"/getValue");
		return Boolean.parseBoolean(result);
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
