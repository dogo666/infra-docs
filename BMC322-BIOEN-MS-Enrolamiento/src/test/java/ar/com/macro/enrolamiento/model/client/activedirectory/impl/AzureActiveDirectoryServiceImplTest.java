package ar.com.macro.enrolamiento.model.client.activedirectory.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import ar.com.macro.config.AzureConfig;
import ar.com.macro.enrolamiento.model.client.activedirectory.dto.GraphApiTokenModel;
import ar.com.macro.enrolamiento.model.client.activedirectory.dto.GraphApiUserResultModel;
import ar.com.macro.exceptions.GeneralException;

@RunWith(SpringRunner.class)
public class AzureActiveDirectoryServiceImplTest {

  @Mock private RestTemplate restTemplate;

  @Mock private AzureConfig azureConfig;

  @InjectMocks private AzureActiveDirectoryServiceImpl activeDirectoryService;

  @Test
  public void getAccessTokenOk() throws JsonMappingException, JsonProcessingException {
    var accessToken = "accessToken";

    when(azureConfig.getTokenUrl()).thenReturn("uri");
    when(azureConfig.getClientId()).thenReturn("clientId");
    when(azureConfig.getClientSecret()).thenReturn("secret");
    when(azureConfig.getScope()).thenReturn("scope");
    when(azureConfig.getTenant()).thenReturn("tenant");

    when(restTemplate.postForObject(
            anyString(), any(HttpEntity.class), eq(GraphApiTokenModel.class), anyMap()))
        .thenReturn(new GraphApiTokenModel(null, null, null, accessToken));

    var response = activeDirectoryService.getAccessToken();

    assertNotNull(response);
    assertEquals(accessToken, response);
  }

  @Test(expected = ResourceAccessException.class)
  public void getAccessTokenException() throws JsonMappingException, JsonProcessingException {

    when(azureConfig.getTokenUrl()).thenReturn("uri");
    when(azureConfig.getClientId()).thenReturn("clientId");
    when(azureConfig.getClientSecret()).thenReturn("secret");
    when(azureConfig.getScope()).thenReturn("scope");
    when(azureConfig.getTenant()).thenReturn("tenant");

    when(restTemplate.postForObject(
            anyString(), any(HttpEntity.class), eq(GraphApiTokenModel.class), anyMap()))
        .thenThrow(new ResourceAccessException(""));

    activeDirectoryService.getAccessToken();
  }

  @Test(expected = GeneralException.class)
  public void getAccessTokenEmptyBodyException()
      throws JsonMappingException, JsonProcessingException {

    when(azureConfig.getTokenUrl()).thenReturn("uri");
    when(azureConfig.getClientId()).thenReturn("clientId");
    when(azureConfig.getClientSecret()).thenReturn("secret");
    when(azureConfig.getScope()).thenReturn("scope");
    when(azureConfig.getTenant()).thenReturn("tenant");

    when(restTemplate.postForObject(
            anyString(), any(HttpEntity.class), eq(GraphApiTokenModel.class), anyMap()))
        .thenReturn(null);

    activeDirectoryService.getAccessToken();
  }

  @Test
  public void isUserInGroupOk() throws JsonMappingException, JsonProcessingException {

    try (MockedStatic<UriComponentsBuilder> utilities =
        Mockito.mockStatic(UriComponentsBuilder.class)) {

      utilities
          .when(() -> UriComponentsBuilder.fromHttpUrl(anyString()))
          .thenReturn(new UriComponentsBuilder() {});

      when(azureConfig.getGroupMembersUrl()).thenReturn("uri");

      when(restTemplate.exchange(
              anyString(),
              eq(HttpMethod.GET),
              any(HttpEntity.class),
              eq(GraphApiUserResultModel.class)))
          .thenReturn(
              new ResponseEntity<GraphApiUserResultModel>(
                  new GraphApiUserResultModel(1, Collections.emptyList()), HttpStatus.OK));

      var response = activeDirectoryService.isUserInGroup("email", "rol", "accessToken");

      assertEquals(true, response);
    }
  }

  @Test
  public void isNotMemberOfGroupOk() throws JsonMappingException, JsonProcessingException {

    try (MockedStatic<UriComponentsBuilder> utilities =
        Mockito.mockStatic(UriComponentsBuilder.class)) {

      utilities
          .when(() -> UriComponentsBuilder.fromHttpUrl(anyString()))
          .thenReturn(new UriComponentsBuilder() {});

      when(azureConfig.getGroupMembersUrl()).thenReturn("uri");

      when(restTemplate.exchange(
              anyString(),
              eq(HttpMethod.GET),
              any(HttpEntity.class),
              eq(GraphApiUserResultModel.class)))
          .thenReturn(
              new ResponseEntity<GraphApiUserResultModel>(
                  new GraphApiUserResultModel(0, Collections.emptyList()), HttpStatus.OK));

      var response = activeDirectoryService.isUserInGroup("email", "rol", "accessToken");

      assertEquals(false, response);
    }
  }

  @Test(expected = ResourceAccessException.class)
  public void isUserInGroupException() throws JsonMappingException, JsonProcessingException {

    try (MockedStatic<UriComponentsBuilder> utilities =
        Mockito.mockStatic(UriComponentsBuilder.class)) {

      utilities
          .when(() -> UriComponentsBuilder.fromHttpUrl(anyString()))
          .thenReturn(new UriComponentsBuilder() {});

      when(azureConfig.getGroupMembersUrl()).thenReturn("uri");

      when(restTemplate.exchange(
              anyString(),
              eq(HttpMethod.GET),
              any(HttpEntity.class),
              eq(GraphApiUserResultModel.class)))
          .thenThrow(new ResourceAccessException(""));

      activeDirectoryService.isUserInGroup("email", "rol", "accessToken");
    }
  }

  @Test(expected = GeneralException.class)
  public void isUserInGroupEmptyBodyError() throws JsonMappingException, JsonProcessingException {

    try (MockedStatic<UriComponentsBuilder> utilities =
        Mockito.mockStatic(UriComponentsBuilder.class)) {

      utilities
          .when(() -> UriComponentsBuilder.fromHttpUrl(anyString()))
          .thenReturn(new UriComponentsBuilder() {});

      when(azureConfig.getGroupMembersUrl()).thenReturn("uri");

      when(restTemplate.exchange(
              anyString(),
              eq(HttpMethod.GET),
              any(HttpEntity.class),
              eq(GraphApiUserResultModel.class)))
          .thenReturn(new ResponseEntity<GraphApiUserResultModel>(HttpStatus.OK));

      activeDirectoryService.isUserInGroup("email", "rol", "accessToken");
    }
  }
}
