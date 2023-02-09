package ar.com.macro.enrolamiento.model.client.esb.rest.dto.consulta.normalizacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("elements")
public class ConsultaNormalizacionIndividuo implements Serializable{

    private static final long serialVersionUID = 4375825727921155924L;
    @JsonProperty("elements")
    private List<Individuo> individuos ;
   
    /**
     * No args constructor for use in serialization
     */
    public ConsultaNormalizacionIndividuo() {
    }

    public ConsultaNormalizacionIndividuo(List<Individuo> individuos) {
        super();
        this.individuos = individuos;
    }

    @JsonProperty("elements")
    public List<Individuo> getIndividuos() {
        return individuos;
    }

    @JsonProperty("elements")
    public void setIndividuos(List<Individuo> individuos) {
        this.individuos = individuos;
    }

}
