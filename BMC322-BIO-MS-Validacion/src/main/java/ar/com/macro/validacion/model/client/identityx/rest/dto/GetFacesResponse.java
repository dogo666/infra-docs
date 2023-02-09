package ar.com.macro.validacion.model.client.identityx.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class GetFacesResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	String href;

	@JsonProperty("id")
	String idCheck;

	Metadata metadata;
	Paging paging;

	Face[] items;
}
