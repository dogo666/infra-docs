package ar.com.macro.exceptions;

import ar.com.macro.commons.exceptions.MacroException;
import ar.com.macro.commons.utils.rest.dto.Error;
import ar.com.macro.constant.Errores;

/**
 * Excepción general 
 *
 * @author globant
 * @version 1.0
 * @since 2022-13-07
 */
public class GeneralException extends MacroException {

  private static final long serialVersionUID = 5146187228272612175L;

  public GeneralException(Integer codigo, String msg) {
    super(codigo, msg);
  }

  public GeneralException(Errores error) {
    super(error.getCodigo(), error.getMensaje());
  }

  public GeneralException(Error error) {
    super(error.getCodigo(), error.getMensaje());
  }
}
