package ar.com.macro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * AzureConfig.
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-21
 */
@Configuration
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AzureConfig {
  
  @Value("${azure.app-registration.client.tenant}")
  String tenant;

  @Value("${azure.app-registration.client.client-id}")
  String clientId;

  @Value("${azure.app-registration.client.client-secret}")
  String clientSecret;

  @Value("${azure.app-registration.client.scope}")
  String scope;

  @Value("${azure.app-registration.client.token-url}")
  String tokenUrl;
  
  @Value("${azure.app-registration.client.group-members-url}")
  String groupMembersUrl;
  
  @Bean("azureRestTemplate")
  public RestTemplate getAzureRestTemplate() {
    return new RestTemplate();
  }
      
}
