/**
 * 
 * @author Moisa Anca-Elena, 321 CA
 *
 */
@SuppressWarnings("serial")
public class MockitoException extends RuntimeException {
	/**
	 * MockitoException constructor
	 */
	public MockitoException() {
		super();
	}

	/**
	 * Message for exception
	 * 
	 * @param message
	 *            - error message
	 */
	public MockitoException(String message) {
		super(message);
	}
}
