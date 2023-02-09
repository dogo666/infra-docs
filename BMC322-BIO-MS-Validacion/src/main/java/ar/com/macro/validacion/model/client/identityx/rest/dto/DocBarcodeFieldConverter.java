package ar.com.macro.validacion.model.client.identityx.rest.dto;

import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Map;

public class DocBarcodeFieldConverter extends StdConverter<Map< String,DocBarcodeField>, Map<String, DocBarcodeField>> {
    @Override
    public Map<String, DocBarcodeField> convert(Map< String,DocBarcodeField> value) {
        return value;
    }
}