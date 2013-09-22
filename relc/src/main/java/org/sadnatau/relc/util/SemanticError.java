package org.sadnatau.relc.util;

/**
 * An exception class for semantic errors.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class SemanticError extends Exception {

    /* impl. serializable. */
	private static final long serialVersionUID = 43L;

	public SemanticError(String msg) {
		super(msg);
	}
}
