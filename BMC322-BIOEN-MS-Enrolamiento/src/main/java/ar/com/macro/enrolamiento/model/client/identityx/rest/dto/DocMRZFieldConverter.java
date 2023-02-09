package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Map;

public class DocMRZFieldConverter extends StdConverter<Map< String,DocMRZField>, Map<String, DocMRZField>> {
    @Override
    public Map<String, DocMRZField> convert(Map< String,DocMRZField> value) {
        return value;
    }
}
