import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Moisa Anca-Elena, 321 CA
 */

public class Mockito {
	private static final class MockableBlackMagic<A, B> {
		private boolean isWatching;
		private Map<Object, Queue<Object>> mReturnabless;
		private Object currentInputType; // Key
		private Unit unit;
		// Create mocked object
		private Mockable<A, B> mockedObject;

		/**
		 * MockableBlackMagic constructor
		 */
		public MockableBlackMagic() {
			isWatching = false;
			mReturnabless = new HashMap<>();
			unit = new Unit();
			mockedObject = new Mockable<A, B>() {

				/**
				 * Execute with 0 parameters
				 * 
				 * @return - actually nothing because Unit class is empty
				 */
				@Override
				public Unit execute() {
					currentInputType = unit;

					// Check for value to return/throw
					Queue<Object> returnabless = mReturnabless.get(unit);

					if (returnabless == null || returnabless.isEmpty()) {
						return null;
					}

					if (returnabless != null && !returnabless.isEmpty()) {
						// If there is only one element, return/throw just that
						if (returnabless.element() instanceof Exception) {
							throw (RuntimeException) returnabless.poll();
						}
					}
					return unit;
				}

				/**
				 * Execute with one parameter
				 * 
				 * @param arg
				 * @return the first element of the queue
				 */
				@SuppressWarnings("unchecked")
				@Override
				public B execute(A arg) {
					currentInputType = arg;

					// Check for value to return/throw
					Queue<Object> returnabless = mReturnabless.get(arg);

					if (returnabless == null || returnabless.isEmpty()) {
						return null;
					}

					if (returnabless != null && !returnabless.isEmpty()) {
						// If there is only one element, return/throw just that
						if (returnabless.element() instanceof Exception) {
							throw (RuntimeException) returnabless.poll();
						}
					}
					return (B) returnabless.poll();
				}
			};
		}

		/**
		 * Set isWatching to true so Mockito can start watch now
		 */
		public void startWatching() {
			isWatching = true;
		}

		/**
		 * Check if Mockito is watching
		 * 
		 * @return isWatching
		 */
		public boolean isWatching() {
			return isWatching;
		}

		/**
		 * Change the value of isWatching to false
		 */
		public void stopWatching() {
			isWatching = false;
		}

		/**
		 * Get the mocked object
		 * 
		 * @return the mocked object
		 */
		public Mockable<A, B> getMockedObject() {
			return mockedObject;
		}

		/**
		 * Store the value that API user wants to return in thenReturn function
		 * 
		 * @param type
		 *            - parameter that will be sent in thenReturn function
		 */
		public void cacheReturnTypeForLastInputType(B value) {
			if (mReturnabless.get(currentInputType) == null) {
				LinkedList<Object> list = new LinkedList<Object>(); // value
				mReturnabless.put(currentInputType, list);
			}
			mReturnabless.get(currentInputType).add(value);
		}

		/**
		 * Store the value that API user wants to return in thenThrow function
		 * 
		 * @param exc
		 *            - parameter that will be sent in thenThrow function
		 */
		public void cacheThrowTypeForLastInputType(Exception exc) {
			if (mReturnabless.get(currentInputType) == null) {
				LinkedList<Object> list = new LinkedList<Object>();
				mReturnabless.put(currentInputType, list);
			}
			mReturnabless.get(currentInputType).add(exc);
		}
	} // End of MockableBlackMagic class

	private static final Mockito sINSTANCE = new Mockito();

	@SuppressWarnings("rawtypes")
	private Stack<MockableBlackMagic> mockables;

	/**
	 * Mockito constructor
	 */
	public Mockito() {
		mockables = new Stack<>();
	}

	/**
	 * Create an image of mocked object
	 * 
	 * @param realObject
	 * @return
	 */
	public static <A, B> Mockable<A, B> mock(Mockable<A, B> realObject) {
		// cache mocked object
		MockableBlackMagic<A, B> mockableBlackMagic = new MockableBlackMagic<A, B>();
		sINSTANCE.mockables.push(mockableBlackMagic);
		return mockableBlackMagic.getMockedObject();
	}

	/**
	 * Mockito is watching
	 * 
	 * @return the instance
	 */
	@SuppressWarnings("rawtypes")
	public static Mockito watch() {
		// get last mocked object
		MockableBlackMagic mock = sINSTANCE.mockables.peek();
		mock.startWatching();
		return sINSTANCE;
	}

	/**
	 * 
	 * @param execute
	 * @return the instance
	 */
	public Mockito when(Object execute) {
		return sINSTANCE;
	}

	/**
	 * 
	 * @param string
	 *            - object to be returned
	 * @return the instance
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Mockito thenReturn(Object string) {
		MockableBlackMagic mock = sINSTANCE.mockables.peek();
		if (mock.isWatching()) {
			mock.cacheReturnTypeForLastInputType(string);
		}
		return sINSTANCE;
	}

	/**
	 * 
	 * @param exc
	 *            - exception to be thrown
	 * @return the instance
	 */
	@SuppressWarnings("rawtypes")
	public Mockito thenThrow(Exception exc) {
		MockableBlackMagic mock = sINSTANCE.mockables.peek();
		if (mock.isWatching()) {
			mock.cacheThrowTypeForLastInputType(exc);
		}
		return sINSTANCE;
	}

	/**
	 * Mockito is not watching anymore
	 */
	public void andBeDoneWithIt() {
		// find last mocked object and stop watching
		sINSTANCE.mockables.peek().stopWatching();
	}

	/**
	 * Does nothing
	 * 
	 * @param mock
	 * @param strategy
	 * @return - just return mock
	 */
	@SuppressWarnings("rawtypes")
	public static Mockable verify(Mockable mock, int strategy) {
		return mock;
	}

	/**
	 * Does nothing
	 * 
	 * @param mock
	 * @return - just return mock
	 */
	@SuppressWarnings("rawtypes")
	public static Mockable verify(Mockable mock) {
		return mock;
	}

	/**
	 * This function is empty :'(
	 * 
	 * @return 0
	 */
	public static int atLeastOnce() {
		return 0;
	}

	/**
	 * This function is empty :'(
	 * 
	 * @return 0
	 */
	public static int exactlyOnce() {
		return 0;
	}

	/**
	 * This function is empty :'(
	 * 
	 * @param n
	 * @return 0
	 */
	public static int times(int n) {
		return 0;
	}
}
