package by.pvt.pintusov.courses.exceptions;

/**
 * Service Exception
 * @author dpintusov
 * @version 1.2
 */
public class ServiceException extends Exception {
	public ServiceException() {
		super();
	}

	public ServiceException(String message){
		super(message);
	}

	public ServiceException(String message, Throwable cause){
		super(message, cause);
	}
}
