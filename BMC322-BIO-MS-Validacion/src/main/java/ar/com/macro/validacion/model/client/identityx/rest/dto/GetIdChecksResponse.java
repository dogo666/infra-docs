package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class GetIdChecksResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	String href;
	Metadata metadata;
	Paging paging;

	CreateEmptyIdCheckResponse[] items;
}



