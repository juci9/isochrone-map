package fr.capgemini.webservices.common;

public class APIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444256640780588256L;

	public APIException() {

	}

	public APIException(String arg0) {
		super(arg0);

	}

	public APIException(Throwable arg0) {
		super(arg0);

	}

	public APIException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

}
