package customeExceptions;

import java.sql.SQLException;

public class VacationAndInitException extends Exception {
	private static final long serialVersionUID = 1L;

	public static void throwNew(String message, Throwable cause) throws VacationAndInitException {
		throw new VacationAndInitException(message, cause);
	}

	public static VacationAndInitException getNew(String message, Throwable cause) throws VacationAndInitException {
		return new VacationAndInitException(message, cause);
	}

	public VacationAndInitException(String message) {
		super(message);
	}

	public VacationAndInitException(Throwable cause) {
		super(cause);
	}

	public VacationAndInitException(String message, Throwable cause) {
		super(message, cause);
	}

}
