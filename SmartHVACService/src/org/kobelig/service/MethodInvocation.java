package org.kobelig.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvocation extends Action {
	private String id;         //ID of the action
	private Object object;     //reference to the object
	private Method method;     //reference to the method
	private Object [] args;    //reference to args
	private String description; //description

	public String getId() {
		return id;
	}


	public Object[] getArgs() {
		return args;
	}


	public void setArgs(Object[] args) {
		this.args = args;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Object getObject() {
		return object;
	}


	public void setObject(Object object) {
		this.object = object;
	}


	public Method getMethod() {
		return method;
	}


	public void setMethod(Method method) {
		this.method = method;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public MethodInvocation() {

	}

	@Override
	public void execute() {
		// TODO 自動生成されたメソッド・スタブ
		try {
			method.invoke(object, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.err.println("Error: Failed to execute action "+ id);
		}
	}

}
