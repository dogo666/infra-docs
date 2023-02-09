package ar.com.macro.datosgenerales.model.repository;

import static ar.com.macro.commons.values.constants.error.CodigoErrorRenaper.INDICADOR_NO_SOPORTADO;
import ar.com.macro.commons.exceptions.FuncionalidadNoSoportadaException;
import ar.com.macro.datosgenerales.model.repository.entity.Trace;

import ar.com.macro.datosgenerales.model.repository.entity.TraceMsEnrolamiento;
import ar.com.macro.datosgenerales.model.repository.entity.TraceMsValidacion;
import ar.com.macro.datosgenerales.model.repository.entity.TraceIdentityX;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * El nombre del enum debe coincidir con el nombre de la iniciatica
 * para hacer el match del metodo valueOf
 */

@Slf4j
public enum TrazaEnum {
    MS_ENROLAMIENTO(TraceMsEnrolamiento::new),
    MS_VALIDACION(TraceMsValidacion::new),
    OUTBOUND_IDENTITYX(TraceIdentityX::new);

    private static Map<Integer, TrazaEnum> traceEnumMap = new HashMap<>();;

    static {
        traceEnumMap.put(1, TrazaEnum.MS_ENROLAMIENTO);
        traceEnumMap.put(2, TrazaEnum.MS_VALIDACION);
        traceEnumMap.put(3, TrazaEnum.OUTBOUND_IDENTITYX);
    }

    private Supplier<Trace> instantiator ;

    public Trace getInstance() {
        return instantiator.get();
    }

    TrazaEnum(Supplier<Trace> instantiator) {
        this.instantiator = instantiator;
    }

    public static TrazaEnum getTraceByInteger(Integer trace){
        try{
            TrazaEnum trazaEnum = traceEnumMap.get(trace);
            if(Objects.isNull(trazaEnum)){
                throw new FuncionalidadNoSoportadaException(Integer.valueOf(INDICADOR_NO_SOPORTADO.getCodigo()), INDICADOR_NO_SOPORTADO.getMensaje());
            }
            return trazaEnum;
        }catch(Exception ex){
            throw new FuncionalidadNoSoportadaException(Integer.valueOf(INDICADOR_NO_SOPORTADO.getCodigo()), INDICADOR_NO_SOPORTADO.getMensaje());
        }
    }

}
