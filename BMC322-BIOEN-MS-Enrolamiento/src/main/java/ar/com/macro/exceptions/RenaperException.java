package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

public class RenaperException extends MacroException {

	private static final long serialVersionUID = 4723754388196577864L;

	public RenaperException(Integer codigo, String msg) {
		super(codigo, msg);
	}

}