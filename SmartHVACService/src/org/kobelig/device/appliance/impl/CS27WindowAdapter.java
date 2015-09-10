package org.kobelig.device.appliance.impl;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.kobelig.device.type.DeviceOperationException;
import org.kobelig.device.type.Window;
import org.kobelig.state.DeviceStatus;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CS27WindowAdapter implements Window {
	private String baseurl;
	private String id;
	private String [] keys = {"id", "state", "openLevel"};
	private DeviceStatus status = new DeviceStatus(keys);

	public CS27WindowAdapter(String baseurl) {
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
	public void open() throws DeviceOperationException {
		String res = invokeWebService(baseurl+"/open");
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}

	}

	@Override
	public void close() throws DeviceOperationException {
		String res = invokeWebService(baseurl+"/close");
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}

	}

	@Override
	public void partlyOpen(int level)  throws DeviceOperationException{
		String res = invokeWebService(baseurl+"/slideWindowAt?level="+level);
		if (Boolean.parseBoolean(res)==false) {
			throw new DeviceOperationException();
		}

	}

	private String invokeWebService(String url)  {
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
