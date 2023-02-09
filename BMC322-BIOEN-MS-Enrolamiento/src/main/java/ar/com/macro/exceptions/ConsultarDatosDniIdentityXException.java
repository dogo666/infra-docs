package ar.com.macro.exceptions;
import ar.com.macro.commons.exceptions.MacroException;

public class ConsultarDatosDniIdentityXException extends MacroException {

    public ConsultarDatosDniIdentityXException(Integer codigo, String msg) {
      super(codigo, msg);
    }
}
