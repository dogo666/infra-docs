package ar.com.macro.validacion.model.service.impl;

import ar.com.macro.commons.utils.business.RenaperSelfieRequest;
import ar.com.macro.commons.values.constants.text.CharConstants;
import ar.com.macro.validacion.domain.enrolamiento.rest.dto.request.HuellaRequest;
import ar.com.macro.validacion.domain.renaper.rest.dto.request.ValidarHuellaEnRenaperRequest;
import ar.com.macro.validacion.domain.renaper.rest.dto.response.ValidarHuellaEnRenaperResponse;
import ar.com.macro.validacion.domain.renaper.rest.dto.request.ValidarDniRenaperRequest;
import ar.com.macro.validacion.model.feign.enrolamiento.dto.request.ValidarRostroRenaperRequest;
import ar.com.macro.validacion.model.client.renaper.HuellaRenaperClient;
import ar.com.macro.validacion.model.client.renaper.RenaperClient;
import ar.com.macro.validacion.model.client.renaper.dto.soap.*;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientRequest;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarDNIRenaperClientResponse;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarRostroRenaperClientRequest;
import ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarRostroRenaperClientResponse;
import ar.com.macro.validacion.model.service.RenaperService;
import ar.com.macro.exceptions.RenaperException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ar.com.macro.constant.Errores;

import java.util.Optional;

import static ar.com.macro.constant.Constantes.RENAPER_CONSULTA_TCN_MENSAJE_OK;
import static ar.com.macro.validacion.model.client.renaper.dto.rest.ValidarRostroRenaperClientRequest.BASE64_FINGER_PRINT;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class RenaperServiceImpl implements RenaperService {

    private final int HUELLA_1 = 0;
    private final int HUELLA_2 = 1;

    @Value("${renaper.tiempo.espera.consulta.tcn}")
    private  int tiempoEsperaTCN ;

    @Value("${renaper.intentos.consulta.tcn}")
    private  int intentosMaximosTCN ;


    private final RenaperClient renaperClient;
    private final HuellaRenaperClient huellaRenaperClient;

    @Override
    public Optional<ValidarDNIRenaperClientResponse> validarIdentidadPersonaPorDNI(ValidarDniRenaperRequest validarDniRenaperRequest){
        ValidarDNIRenaperClientRequest validarDNIRenaperClientRequest = ValidarDNIRenaperClientRequest.builder()
                .gender(validarDniRenaperRequest.getGenero())
                .number(validarDniRenaperRequest.getNumero())
                .order(validarDniRenaperRequest.getOrden())
                .build();
        return renaperClient.validarDni(validarDNIRenaperClientRequest);
    }

    @Override
    public Optional<ValidarRostroRenaperClientResponse> validarIdentidadPersonaPorRostro(ValidarRostroRenaperRequest validarRostroRenaperRequest){

        ValidarRostroRenaperClientRequest identificacionRenaperPersonaRostroRequest = ValidarRostroRenaperClientRequest.builder()
                .gender(validarRostroRenaperRequest.getGenero())
                .number(validarRostroRenaperRequest.getNumero())
                .selfieList(validarRostroRenaperRequest.getSelfies().stream()
                        .map(v -> new RenaperSelfieRequest(v.getArchivo(),v.getTipo())).collect(toList()))
                .browserFingerprintData(BASE64_FINGER_PRINT)
                .build();
        return renaperClient.validarRostro(identificacionRenaperPersonaRostroRequest);
    }


    @Override
    public ValidarHuellaEnRenaperResponse validarHuellaEnRenaper(
            ValidarHuellaEnRenaperRequest validarHuellaEnRenaperRequest)  {

        ObjectFactory of = new ObjectFactory();

        GenerarTransaccion generarTransaccion = of.createGenerarTransaccion();
        Entrada entrada = of.createEntrada();
        entrada.setDni(Integer.valueOf(validarHuellaEnRenaperRequest.getDni()));
        entrada.setSexo(validarHuellaEnRenaperRequest.getSexo());
        HuellaRequest huella = validarHuellaEnRenaperRequest.getHuellas().get(HUELLA_1);
        entrada.setHuella1(huella.getHuella());
        entrada.setDescripcionH1(huella.getIdentificador());
        huella = validarHuellaEnRenaperRequest.getHuellas().get(HUELLA_2);
        entrada.setHuella2(huella.getHuella());
        entrada.setDescripcionH2(huella.getIdentificador());
        generarTransaccion.setEntrada(entrada);

        GenerarTransaccionResponse generarTransaccionResponse = huellaRenaperClient.generarTransaccion(generarTransaccion);
        String tcn = generarTransaccionResponse.getTcn().split(CharConstants.CARACTER_ESPACIO_BLANCO)[0];

        if(esCodigoTCNInvalido(tcn)) {
            throw new RenaperException(Errores.ERROR_GENERAR_TRANSACCION.getCodigo(), Errores.ERROR_GENERAR_TRANSACCION.getMensaje());
        }
        ConsultaPorTCN consultaPorTCN = of.createConsultaPorTCN();
        consultaPorTCN.setTcn(tcn);

        int intentos = 0;
        Salida salida ;
        ConsultaPorTCNResponse consultaPorTCNResponse;
        do {
            try {
                Thread.sleep(tiempoEsperaTCN);
            }catch (InterruptedException e){
                throw new RenaperException(Errores.ERROR_ESPERA_RESULTADO_VALIDACION_HUELLA.getCodigo(), Errores.ERROR_ESPERA_RESULTADO_VALIDACION_HUELLA.getMensaje());
            }
                consultaPorTCNResponse = huellaRenaperClient.consultarPorTCN(consultaPorTCN);
                salida = consultaPorTCNResponse.getSalida();
                intentos++;

        } while (intentos<intentosMaximosTCN && !RENAPER_CONSULTA_TCN_MENSAJE_OK.equals(salida.getMensaje()));

        if(!RENAPER_CONSULTA_TCN_MENSAJE_OK.equals(salida.getMensaje())){
        	log.error("Renaper Error - {}, codigo: {}, mensaje: {}", Errores.ERROR_CONSULTAR_TCN.getMensaje(), salida.getCodigo(), salida.getMensaje());
            throw new RenaperException(Errores.ERROR_CONSULTAR_TCN.getCodigo(), Errores.ERROR_CONSULTAR_TCN.getMensaje());
        }

        ValidarHuellaEnRenaperResponse validarHuellaEnRenaperResponse = new ValidarHuellaEnRenaperResponse();
        validarHuellaEnRenaperResponse.setPuntaje(salida.getMatchScore());
        validarHuellaEnRenaperResponse.setResultado(salida.getMatchType());
        return validarHuellaEnRenaperResponse;
    }

    private boolean esCodigoTCNInvalido(String tcn) {
        return tcn.startsWith(CharConstants.CARACTER_GUION);
    }
}
