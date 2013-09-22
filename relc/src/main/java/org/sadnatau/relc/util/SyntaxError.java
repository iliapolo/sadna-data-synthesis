package org.sadnatau.relc.util;

/**
 * 
 * An exception class for syntax errors.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class SyntaxError extends Exception {

    /* impl. serializable. */
	private static final long serialVersionUID = 43L;

	public SyntaxError(String msg) {
		super(msg);
	}
}
