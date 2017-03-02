package by.pvt.pintusov.courses.exceptions;

/**
 * Creates DaoException
 * @author pintusov
 * @version 1.2
 */
public class DaoException extends Exception {
	public DaoException () {super();}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
