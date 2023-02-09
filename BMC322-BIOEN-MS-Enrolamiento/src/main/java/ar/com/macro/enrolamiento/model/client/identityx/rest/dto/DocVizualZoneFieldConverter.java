package ar.com.macro.enrolamiento.model.client.identityx.rest.dto;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Map;

public class DocVizualZoneFieldConverter extends StdConverter<Map< String,DocVizualZoneField>, Map<String, DocVizualZoneField>> {
    @Override
    public Map<String, DocVizualZoneField> convert(Map< String,DocVizualZoneField> value) {
        return value;
    }
}
