package org.kobelig.com.acd;

public class ACDExecException extends Exception {
	  private static final long serialVersionUID = 1L;
	    private int code;
	    public ACDExecException(int code, String message) {
	        super(message);
	        this.code = code;
	    }
	    public int getCode() {
	        return code;
	    }
}
