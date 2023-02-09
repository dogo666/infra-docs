package ar.com.macro.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errores {
  ERROR_EN_LA_CONSULTA_ENROLADOR(75000, "Ocurrió un error al realizar la consulta del enrolador."),
  ERROR_ENROLADOR_NO_ENCONTRADO(75001, "No se encontraron datos para el enrolador"),
  ERROR_GUARDAR_ENROLADOR(75002, "Ocurrió un error al guardar el enrolador"),
  ERROR_CONSULTAR_ENROLADORES(75003, "Ocurrió un error al filtrar los enroladores por estado y sucursal"),
  ERROR_CONSULTAR_SUCURSALES_ENROLADOR_PENDIENTE(75004, "Ocurrió un error al consultar las sucursales con enrolador en estado pendiente.");

  private Integer codigo;
  private String mensaje;
}
