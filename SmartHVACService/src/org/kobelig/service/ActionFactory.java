package org.kobelig.service;

import java.lang.reflect.Method;

public class ActionFactory {
	public Action create(String id, Object object, String method, Object [] args, String description) {

		Method [] methods = object.getClass().getMethods();

		for (Method m: methods) {
			if (method.equals(m.getName())) {
				//method is found!
				Class<?>[] params = m.getParameterTypes();
				for (int i=0; i<params.length; i++) {
					if (params[i] != args[i].getClass()) {
//						System.err.print("Warning: Parameter type mismatch -- ");
//						System.err.println(params[i].getName() + " == " + args[i].getClass().getName());
					}
				}
			MethodInvocation action = new MethodInvocation();
			action.setId(id);
			action.setObject(object);
			action.setArgs(args);
			action.setMethod(m);
			action.setDescription(description);
			return action;
			}
		}
		System.err.println("Error: No method " + object.getClass().getName() + "."+method + "() found.");

		return null;

	}

}
