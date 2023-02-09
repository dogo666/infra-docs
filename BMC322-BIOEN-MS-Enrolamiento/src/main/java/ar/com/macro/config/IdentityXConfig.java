package ar.com.macro.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.identityx.clientSDK.TenantRepoFactory;
import com.identityx.clientSDK.credentialsProviders.EncryptedKeyPropFileCredentialsProvider;
import com.identityx.clientSDK.exceptions.ClientInitializationException;
import com.identityx.clientSDK.exceptions.IdxRestException;
import ar.com.macro.enrolamiento.model.service.DigitalOnboardingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@AllArgsConstructor
public class IdentityXConfig implements ApplicationContextAware {

  private static ApplicationContext context;

  private Environment env;

  private Properties createBaseIdentityXProperties() {
    var properties = new Properties();
    properties.put("sharedKeyId", env.getProperty("identityx.digest-auth.shared.key.id"));
    properties.put(
        "encryptedSharedKey", env.getProperty("identityx.digest-auth.encrypted.shared.key"));
    properties.put("created", env.getProperty("identityx.digest-auth.created"));
    properties.put("expiration", env.getProperty("identityx.digest-auth.expiration"));
    properties.put("subjectId", env.getProperty("identityx.digest-auth.subjectId"));
    properties.put("tenant", env.getProperty("identityx.digest-auth.tenant"));
    properties.put("authType", env.getProperty("identityx.digest-auth.authType"));
    return properties;
  }

  private InputStream setupIdentityXKeystore() {
    var loader = Thread.currentThread().getContextClassLoader();
    var keystore =
        loader.getResourceAsStream(
            "identity-x-setup/" + env.getProperty("identityx.digest-auth.keystore"));
    return keystore;
  }

  // --

  @Bean
  public DigitalOnboardingRepository digitalOnboardingRepository()
      throws IOException, IdxRestException, ClientInitializationException {

    var url = env.getProperty("identityx.onboarding.host.url");
    log.info("creando DigitalOnboardingRepository con url: {}", url);

    var properties = createBaseIdentityXProperties();
    properties.put("serviceUrl", url);

    var outputStream = new ByteArrayOutputStream();
    properties.store(outputStream, null);
    var inputstreamProperties = new ByteArrayInputStream(outputStream.toByteArray());

    var keystore = setupIdentityXKeystore();

    return new DigitalOnboardingRepository(
        new EncryptedKeyPropFileCredentialsProvider(
            keystore,
            env.getProperty("identityx.digest-auth.keystore.pass"),
            inputstreamProperties,
            null,
            null));
  }

  // --

  @Bean
  public TenantRepoFactory tenantRepoFactory()
      throws ClientInitializationException, IOException, IdxRestException {

    var url = env.getProperty("identityx.host") + env.getProperty("identityx.url");
    log.info("creando TenantRepoFactory con url: {}", url);
    var properties = createBaseIdentityXProperties();
    properties.put("serviceUrl", url);

    var outputStream = new ByteArrayOutputStream();
    properties.store(outputStream, null);
    var inputstreamProperties = new ByteArrayInputStream(outputStream.toByteArray());
    var keystore = setupIdentityXKeystore();

    return new TenantRepoFactory(
        new EncryptedKeyPropFileCredentialsProvider(
            keystore,
            env.getProperty("identityx.digest-auth.keystore.pass"),
            inputstreamProperties,
            null,
            null));
  }
  public static ApplicationContext getApplicationContext() {
    return context;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext)
          throws BeansException {
    context = applicationContext;
  }

}
