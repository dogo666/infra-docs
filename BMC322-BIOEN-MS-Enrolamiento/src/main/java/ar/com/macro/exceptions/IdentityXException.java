package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.constant.Errores;

public class IdentityXException extends MacroException {

	private static final long serialVersionUID = -2472947870984411741L;

	public IdentityXException(Integer codigo, String msg) {
		super(codigo, msg);
	}

	public IdentityXException(Errores error) {
		super(error.getCodigo(),error.getMensaje());
	}
}
