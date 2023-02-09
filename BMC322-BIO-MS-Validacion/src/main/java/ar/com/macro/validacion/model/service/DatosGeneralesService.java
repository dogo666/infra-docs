package ar.com.macro.validacion.model.service;

import ar.com.macro.validacion.model.service.dto.ConfiguracionHuellasDto;

public interface DatosGeneralesService {
    ConfiguracionHuellasDto obtenerDatosGeneralesCompuestos(String xOperationID, String nombreMicroservicio, String nombreParametros);
    String obtenerConfiguracionAplicacionIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros);
    String obtenerConfiguracionPoliticaIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros);
    String obtenerConfiguracionPoliticaEvaluacionIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros);
    Double obtenerConfiguracionUmbralPoliticaDaonEngine(String xOperationID, String nombreMicroservicio, String nombreParametros);
    String obtenerConfiguracionValidacionRenaper3dflIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros);
    Integer obtenerConfiguracionUmbralRenaper3dflIdentityX(String xOperationID, String nombreMicroservicio, String nombreParametros);
}
