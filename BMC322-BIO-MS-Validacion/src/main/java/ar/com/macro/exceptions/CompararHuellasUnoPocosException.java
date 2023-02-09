package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

/**
 * CompararHuellasUnoPocosException
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-28
 */
public class CompararHuellasUnoPocosException extends MacroException {
  private static final long serialVersionUID = 5625068038471847810L;

  public CompararHuellasUnoPocosException(Integer codigo, String msg) {
		super(codigo, msg);
	}

}