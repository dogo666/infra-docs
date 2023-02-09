package ar.com.macro.validacion.model.client.identityx.rest.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateList {

	Candidate[] items;
	Integer size;
}
