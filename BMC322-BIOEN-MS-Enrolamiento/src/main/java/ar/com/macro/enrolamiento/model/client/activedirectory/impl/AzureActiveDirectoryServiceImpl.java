package ar.com.macro.enrolamiento.model.client.activedirectory.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ar.com.macro.config.AzureConfig;
import ar.com.macro.constant.Errores;
import ar.com.macro.enrolamiento.model.client.activedirectory.ActiveDirectoryService;
import ar.com.macro.enrolamiento.model.client.activedirectory.dto.GraphApiTokenModel;
import ar.com.macro.enrolamiento.model.client.activedirectory.dto.GraphApiUserResultModel;
import ar.com.macro.exceptions.GeneralException;
import lombok.extern.slf4j.Slf4j;

/**
 * AzureActiveDirectoryServiceImpl.
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-18
 */
@Slf4j
@Service
public class AzureActiveDirectoryServiceImpl implements ActiveDirectoryService {

  // Headers
  private static final String CLIENT_ID_HEADER = "client_id";
  private static final String CLIENT_SECRET_HEADER = "client_secret";
  private static final String SCOPE_HEADER = "scope";
  private static final String GRANT_TYPE_HEADER = "grant_type";
  private static final String CONSISTENCY_LEVEL_HEADER = "ConsistencyLevel";

  private static final String GRANT_TYPE_VALUE = "client_credentials";
  private static final String CONSISTENCE_LEVEL_VALUE = "eventual";

  @Autowired private AzureConfig azureConfig;

  @Autowired RestTemplate azureRestTemplate;

  @Override
  public String getAccessToken() {
    log.info("Obteniendo token de acceso para consumir API de Graph");

    var headers = new HttpHeaders();

    var body = new LinkedMultiValueMap<>();
    body.add(CLIENT_ID_HEADER, azureConfig.getClientId());
    body.add(CLIENT_SECRET_HEADER, azureConfig.getClientSecret());
    body.add(SCOPE_HEADER, azureConfig.getScope());
    body.add(GRANT_TYPE_HEADER, GRANT_TYPE_VALUE);

    Map<String, Object> uriVariables = new HashMap<>();
    uriVariables.put("tenant", azureConfig.getTenant());

    return Optional.ofNullable(
            azureRestTemplate.postForObject(
                azureConfig.getTokenUrl(),
                new HttpEntity<>(body, headers),
                GraphApiTokenModel.class,
                uriVariables))
        .orElseThrow(() -> new GeneralException(Errores.ERROR_VALIDANDO_USUARIO_GRUPO_AD))
        .getAccessToken();
  }

  @Override
  public boolean isUserInGroup(final String email, final String group, final String token) {
    log.info("Validando que el usuario {} es miembro del grupo {}", email, group);

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);
    headers.add(CONSISTENCY_LEVEL_HEADER, CONSISTENCE_LEVEL_VALUE);

    Map<String, Object> uriVariables = new HashMap<>();
    uriVariables.put("groupId", group);

    var builder =
        UriComponentsBuilder.fromHttpUrl(azureConfig.getGroupMembersUrl())
            .queryParam("$count", Boolean.TRUE)
            .queryParam("$select", "mail")
            .queryParam("$filter", String.format("startswith(mail, '%s')", email))
            .uriVariables(uriVariables)
            .build();

    var users =
        Optional.ofNullable(
                azureRestTemplate
                    .exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        GraphApiUserResultModel.class)
                    .getBody())
            .orElseThrow(() -> new GeneralException(Errores.ERROR_VALIDANDO_USUARIO_GRUPO_AD));

    return users.getCount() > 0;
  }
}
