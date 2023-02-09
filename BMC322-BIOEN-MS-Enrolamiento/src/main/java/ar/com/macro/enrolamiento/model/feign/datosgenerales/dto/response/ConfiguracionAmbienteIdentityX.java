package ar.com.macro.enrolamiento.model.feign.datosgenerales.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguracionAmbienteIdentityX {

	@JsonProperty("local")
	private String local;
	@JsonProperty("dev1")
	private String dev;
	@JsonProperty("stg1")
	private String stg;
	@JsonProperty("lab1")
	private String lab;
	@JsonProperty("test1")
	private String test;
	@JsonProperty("pre-prd1")
	private String preProd1;
	@JsonProperty("pre-prd2")
	private String preProd2;
	@JsonProperty("prd1")
	private String prod1;
	@JsonProperty("prd2")
	private String prod2;
}
