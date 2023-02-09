package ar.com.macro.enrolamiento.model.service.mapper;

import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.ActualizarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.domain.daonengine.rest.dto.request.EnrolarHuellaDaonEngineRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.BiometricDataElement;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.CreateIdentityRequest;
import ar.com.macro.enrolamiento.model.client.daonengine.dto.soap.UpdateIdentityRequest;
import ar.com.macro.exceptions.DaonEngineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static ar.com.macro.constant.Errores.*;

@Slf4j
@Component
public class GenerarListaBiometricDataMapper {

    private int typeQualifier;

    public GenerarListaBiometricDataMapper(@Value("${daonengine.typequalifier}") int typeQualifier){
        this.typeQualifier = typeQualifier;
    }

    public CreateIdentityRequest generarListaBiometricDataEnrolar(CreateIdentityRequest createIdentityRequest, EnrolarHuellaDaonEngineRequest enrolarHuellaDaonEngineRequest){
        try {
            enrolarHuellaDaonEngineRequest.getHuellas().stream().forEach(huella -> {
                BiometricDataElement biometricDataElement = new BiometricDataElement();
                BiometricDataElement.Data data = new BiometricDataElement.Data();
                data.setValue(huella.getImagen());
                biometricDataElement.setTypeQualifier(typeQualifier);
                biometricDataElement.setUsageQualifier(huella.getIdentificador());
                biometricDataElement.setData(data);
                createIdentityRequest.getIdentity().getBiometricData().add(biometricDataElement);
            });
            return createIdentityRequest;
        } catch (DaonEngineException e) {
            log.error(ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ENROLAR.getMensaje(),e);
            throw new DaonEngineException(ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ENROLAR.getCodigo(),ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ENROLAR.getMensaje());
        }
    }

    public UpdateIdentityRequest generarListaBiometricDataActualizar(UpdateIdentityRequest updateIdentityRequest, ActualizarHuellaDaonEngineRequest actualizarHuellaDaonEngineRequest){
        try {
            actualizarHuellaDaonEngineRequest.getHuellas().stream().forEach(huella -> {
                BiometricDataElement biometricDataElement = new BiometricDataElement();
                BiometricDataElement.Data data = new BiometricDataElement.Data();
                data.setValue(huella.getImagen());
                biometricDataElement.setTypeQualifier(typeQualifier);
                biometricDataElement.setUsageQualifier(huella.getIdentificador());
                biometricDataElement.setData(data);
                updateIdentityRequest.getUpdateData().getBiometricData().add(biometricDataElement);
            });
            return updateIdentityRequest;
        } catch (NullPointerException e) {
            log.error(ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ACTUALIZAR.getMensaje(),e);
            throw new DaonEngineException(ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ACTUALIZAR.getCodigo(),ERROR_GENERANDO_LA_LISTA_BIOMETRICA_ACTUALIZAR.getMensaje());
        }
    }
}
