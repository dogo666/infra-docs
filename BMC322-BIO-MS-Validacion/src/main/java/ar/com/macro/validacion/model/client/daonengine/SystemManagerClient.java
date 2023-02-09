package ar.com.macro.validacion.model.client.daonengine;

import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.ListSummaryAuditsRequest;
import ar.com.macro.validacion.model.client.daonengine.systemmanagement.dto.soap.ListSummaryAuditsResponse;

import java.util.Optional;

public interface SystemManagerClient {

	Optional<ListSummaryAuditsResponse> obtenerListSummaryAuditsResponse(ListSummaryAuditsRequest listSummaryAuditsRequest);

}
