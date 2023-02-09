package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.constant.Errores;

public class AgregarDniPerfilException extends MacroException {

	private static final long serialVersionUID = 1082041564612225172L;

	public AgregarDniPerfilException(Integer codigo, String msg) {
		super(codigo, msg);
	}
	public AgregarDniPerfilException(Errores error) {
		super(error.getCodigo(),error.getMensaje());
	}
}
