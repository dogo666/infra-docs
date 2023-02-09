package ar.com.macro.enrolamiento.model.service;

import java.util.HashMap;
import org.slf4j.MDC;
import org.springframework.web.util.UriComponentsBuilder;
import com.identityx.auth.impl.QueryString;
import com.identityx.auth.support.RestException;
import com.identityx.clientSDK.exceptions.IdxRestException;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DigitalOnboardingSDKUtils.
 *
 * @author globant
 * @version 1.0
 * @since 2021-11-25
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DigitalOnboardingSDKUtils {

  private static final String IDENTITY_X_ENDPOINT_DURATION = "IdentityXEndpointDuration";

  // --

  public static final Tuple3<HashMap<String, String>, QueryString, String> defineRequestArtifacts(
      final String templateUri, final Object... uriVariableValues) {

    var requestSpecificHeaders = new HashMap<String, String>();
    var queryString = new QueryString();
    var uri =
        UriComponentsBuilder.fromUriString(templateUri).build().expand(uriVariableValues).getPath();

    return Tuple.of(requestSpecificHeaders, queryString, uri);
  }

  // --

  public static final <T> T callAndMeasureTime(final SDKCall<T> sdkCall, final String uri)
      throws IdxRestException {

    var begin = 0L;
    var duration = 0L;

    try {

      begin = System.currentTimeMillis();
      MDC.put(IDENTITY_X_ENDPOINT_DURATION, "");
      var response = sdkCall.call();
      duration = System.currentTimeMillis() - begin;
      log.info("identity-X url: {}, response time: {} ms", uri, duration);
      return response;

    } catch (IdxRestException e) {
      log.error(
          "identity-X error - status: {}, message: {}, developer-message: {}, code: {}",
          e.getHttpStatus(),
          e.getMessage(),
          e.getDeveloperMessage(),
          e.getCode());
      throw e;
    } catch (RestException e) {
      log.error("identity-X error - status: {}, message: {}", e.getHttpCode(), e.getMessage());
      throw e;
    } finally {
      MDC.put(IDENTITY_X_ENDPOINT_DURATION, String.valueOf(duration));
    }
  }
}

/**
 * SDKCall.
 *
 * @author globant
 * @version 1.0
 * @since 2021-11-25
 */
interface SDKCall<T> {

  T call() throws IdxRestException;
}
