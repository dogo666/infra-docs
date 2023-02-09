package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;

/**
 * ConsultarEnroladoresException.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-19
 */
public class ConsultarEnroladoresException extends MacroException {
  private static final long serialVersionUID = 4532634962237577908L;

  public ConsultarEnroladoresException(Integer codigo, String msg) {
    super(codigo, msg);
  }
}
