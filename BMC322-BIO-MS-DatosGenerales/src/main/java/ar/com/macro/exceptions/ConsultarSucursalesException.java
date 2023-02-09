package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

/**
 * ConsultarSucursalesException.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-20
 */
public class ConsultarSucursalesException extends MacroException {
  private static final long serialVersionUID = 4532634962237577908L;

  public ConsultarSucursalesException(Integer codigo, String msg) {
    super(codigo, msg);
  }
}
