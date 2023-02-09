/*
 * Copyright Daon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.identityx.clientSDK.base;

import ar.com.macro.biometria.commons.trace.util.outbound.identityx.HttpInterceptorIdentityx;
import ar.com.macro.biometria.commons.trace.util.outbound.identityx.IRequestIdentityXWrapper;
import ar.com.macro.biometria.commons.trace.util.outbound.identityx.IResponseIdentityXWrapper;
import ar.com.macro.utils.IdentityxWrapper;
import com.daon.identityx.rest.model.pojo.ActivityDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.identityx.auth.client.HttpClientRequestExecutor;
import com.identityx.auth.def.IApiKey;
import com.identityx.auth.def.IRequest;
import com.identityx.auth.def.IRequestExecutor;
import com.identityx.auth.def.IResponse;
import com.identityx.auth.impl.*;
import com.identityx.auth.impl.keys.ADCredentialsKey;
import com.identityx.auth.impl.keys.PrivateApiKey;
import com.identityx.auth.support.DefaultRequest;
import com.identityx.auth.support.RestException;
import com.identityx.clientSDK.custom.ActivityDefinitionDeserializer;
import com.identityx.clientSDK.custom.ActivityDefinitionSerializer;
import com.identityx.clientSDK.exceptions.IdxRestException;
import com.identityx.clientSDK.queryHolders.QueryHolder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static ar.com.macro.config.IdentityXConfig.getApplicationContext;


/**
 * This class is used to perform the calls to the Rest service.
 * It uses Jackson to serialize/deserialize Java objects to the JSON format to be passed further to/from a {@link HttpClientRequestExecutor}
 *
 */
public class RestClient {

    private IRequestExecutor requestExecutor = null;
    private ObjectMapper mapper = null;
    private IApiKey apiKey;
    private Proxy proxy = null;
    private String cookie = null;
    private SSLConnectionSocketFactory sslCSF;

    private HttpHeaders headers = new HttpHeaders();

    public static class RestClientBuilder {

        private IRequestExecutor requestExecutor = null;
        private ObjectMapper mapper = null;
        private IApiKey apiKey;
        private IApiKey responseApiKey;
        private Proxy proxy = null;
        private String cookie = null;
        private SSLConnectionSocketFactory sslCSF;
        private boolean ignoreCookies = true;
        private boolean useBasicIDX = false;
        private HttpHeaders headers;

        public RestClientBuilder() {

        }

        public RestClientBuilder setRequestExecutor(IRequestExecutor requestExecutor) {
            this.requestExecutor = requestExecutor;
            if (requestExecutor instanceof HttpClientRequestExecutor) {
                this.apiKey = ((HttpClientRequestExecutor)requestExecutor).getApiKey();
            }
            return this;
        }

        public RestClientBuilder setIgnoreCookies(boolean ignoreCookies) {
            this.ignoreCookies = ignoreCookies;
            return this;
        }

        public RestClientBuilder setApiKey(IApiKey apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public RestClientBuilder setBasicIDX(boolean useBasicIDX) {
            this.useBasicIDX = useBasicIDX;
            return this;
        }

        public RestClientBuilder setResponseApiKey(IApiKey responseApiKey) {
            this.responseApiKey = responseApiKey;
            return this;
        }

        public RestClientBuilder setProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public RestClientBuilder setHeaders(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        public RestClientBuilder setCookie(String cookie) {
            this.cookie = cookie;
            return this;
        }

        public RestClientBuilder setSSLConnectionSocketFactory(SSLConnectionSocketFactory sslCSF) {
            this.sslCSF = sslCSF;
            return this;
        }

        /**
         * Don't need to set this
         * @param mapper
         * @return
         */
        public RestClientBuilder setObjectMapper(ObjectMapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public RestClient build() {

            RestClient restClient = new RestClient();
            if (mapper != null) {
                restClient.setObjectMapper(mapper);
            }
            if (headers != null) {
                restClient.setHeaders(headers);
            }
            if (apiKey != null) {
                restClient.setApiKey(apiKey);
            }
            restClient.sslCSF = sslCSF;
            restClient.setProxy(proxy);

            if (requestExecutor == null) {
                if (useBasicIDX) {
                    restClient.init(apiKey, useBasicIDX, sslCSF, ignoreCookies);
                }
                else {
                    restClient.init(apiKey, responseApiKey, sslCSF, ignoreCookies);
                }
            }
            else {
                restClient.setRequestExecutor(requestExecutor);
            }
            restClient.setCookie(cookie);
            return restClient;
        }
    }


    /**
     *
     * @return Jackson object mapper used for serialization/deserialization
     */
    public ObjectMapper getObjectMapper() {
        return mapper;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }
    /**
     *
     * @param mapper Jackson object mapper used for serialization/deserialization
     */
    public void setObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Use this to have access to the configuration of the underlying HttpClient
     * @return {@link HttpClientRequestExecutor}
     */
    protected IRequestExecutor getRequestExecutor() {
        return requestExecutor;
    }

    /**
     *
     * @param requestExecutor {@link HttpClientRequestExecutor}
     */
    protected void setRequestExecutor(IRequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public RestClient() {

        if (mapper == null) mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(ActivityDefinition.class, new ActivityDefinitionDeserializer());
        module.addSerializer(ActivityDefinition.class, new ActivityDefinitionSerializer());
        mapper.registerModule(module);

        mapper.enableDefaultTyping();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accept", "application/json");

        conectToSpringContext();

    }

    private HttpInterceptorIdentityx httpResponseInterceptor;

    private void conectToSpringContext() {
        httpResponseInterceptor = getApplicationContext().getBean( HttpInterceptorIdentityx.class);
    }

    /**
     * Initialises a new {@code RestClient} using the specified {@code SharedSecretApiKey}
     * @param apiKey the account API Key
     */
    public void init(IApiKey apiKey, SSLConnectionSocketFactory sslCSF, boolean ignoreCookies) {

        this.apiKey = apiKey;

        if (requestExecutor == null) {
            if (apiKey == null) {
                requestExecutor = new HttpClientRequestExecutor.HttpClientRequestExecutorBuilder().setIgnoreCookies(ignoreCookies).build();
            }
            else if (apiKey instanceof ADCredentialsKey) {
                requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.BASIC, sslCSF, ignoreCookies);
            }
            else if (apiKey instanceof PrivateApiKey) {
                //requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.JWS, sslCSF);
                throw new IllegalArgumentException("Cannot use a AsymApiKey key; try initializing with the other method");
            }
            else {
                requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.DIGEST, sslCSF, ignoreCookies);
            }
        }
    }

    public void init(IApiKey apiKey, boolean useBasiIDX, SSLConnectionSocketFactory sslCSF, boolean ignoreCookies) {

        this.apiKey = apiKey;

        if (requestExecutor == null) {
            if (apiKey == null) {
                requestExecutor = new HttpClientRequestExecutor.HttpClientRequestExecutorBuilder().setIgnoreCookies(ignoreCookies).build();
            }
            else if (apiKey instanceof ADCredentialsKey) {
                if (useBasiIDX) {
                    requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.BASIC_IDX, sslCSF, ignoreCookies);
                }
                else {
                    requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.BASIC, sslCSF, ignoreCookies);
                }
            }
            else if (apiKey instanceof PrivateApiKey) {
                //requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.JWS, sslCSF);
                throw new IllegalArgumentException("Cannot use a AsymApiKey key; try initializing with the other method");
            }
            else {
                requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.DIGEST, sslCSF, ignoreCookies);
            }
        }
    }


    public void init(IApiKey apiKey, IApiKey serverResponseApiKey, SSLConnectionSocketFactory sslCSF, boolean ignoreCookies) {

        this.apiKey = apiKey;

        if (requestExecutor == null) {
            if (apiKey == null) {
                requestExecutor = new HttpClientRequestExecutor.HttpClientRequestExecutorBuilder().setIgnoreCookies(ignoreCookies).build();
            }
            else if (apiKey instanceof ADCredentialsKey) {
                requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.BASIC, sslCSF, ignoreCookies);
            }
            else if (apiKey instanceof PrivateApiKey) {
                requestExecutor = new HttpClientRequestExecutor(apiKey, serverResponseApiKey, proxy, AuthenticationScheme.JWS, sslCSF, ignoreCookies);
            }
            else {
                requestExecutor = new HttpClientRequestExecutor(apiKey, proxy, AuthenticationScheme.DIGEST, sslCSF, ignoreCookies);
            }
        }
    }


    /**
     * Serialises an object to JSON and posts it to a Rest service, de-serialises the response back to the required type
     * @param obj object to be posted
     * @param href url of the Rest service
     * @param responseType type the response will be deserialised to
     * @return the deserialised resource returned by the Rest service
     * @throws IdxRestException
     */
    public <T> T post(Object obj, String href, Class<T> responseType) throws IdxRestException {

        return this.post(obj, href, new QueryString(), responseType, null);
    }

    /**
     * Serialises an object to JSON and posts it to a Rest service, de-serialises the response back to the required type
     * @param obj object to be posted
     * @param href url of the Rest service
     * @param queryString {@link QueryString} object holding the query string, can be null
     * @param responseType type the response will be deserialised to
     * @return the deserialised resource returned by the Rest service
     * @throws IdxRestException
     */
    public <T> T post(Object obj, String href, QueryString queryString, Class<T> responseType) throws IdxRestException {

        return this.post(obj, href, queryString, responseType, null);
    }

    public <T> T post(Object obj, String href, Class<T> responseType, HashMap<String, String> requestSpecificHeaders) throws IdxRestException {

        return this.post(obj, href, new QueryString(), responseType, requestSpecificHeaders);
    }

    public <T> T post(Object obj, String href, QueryString queryString, Class<T> responseType, HashMap<String, String> requestSpecificHeaders) throws IdxRestException {

        if (requestExecutor == null) throw new IllegalStateException("Object not initialised");

        byte bodyBytes[] = null;
        try {
            bodyBytes = mapper.writeValueAsBytes(obj);
        }
        catch (Exception ex) {
            throw new IdxRestException("Failed to make the REST call: before calling, failed to serialize the passed object to JSON", ex);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bodyBytes);
        int length = bis.available();

        HttpHeaders allHeaders = headers.clone();
        if (requestSpecificHeaders != null && !requestSpecificHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : requestSpecificHeaders.entrySet()) {
                allHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        IRequest request = new DefaultRequest(HttpMethod.POST, href, queryString, allHeaders, bis, length);

        return executeRequest(request, responseType);
    }

    /**
     * Executes a get operation on a Rest service
     * @param queryString {@link QueryString} object holding the query string, can be null
     * @param href url of the Rest service
     * @param responseType type the response will be deserialised to
     * @return the deserialised resource returned by the Rest service
     * @throws IdxRestException
     */
    public <T> T get(QueryString queryString, String href, Class<T> responseType) throws IdxRestException {

        return this.get(queryString, href, responseType, null);
    }

    public <T> T get(QueryString queryString, String href, Class<T> responseType, HashMap<String, String> requestSpecificHeaders) throws IdxRestException {

        if (requestExecutor == null) throw new IllegalStateException("Object not initialised");

        HttpHeaders allHeaders = headers.clone();
        if (requestSpecificHeaders != null && !requestSpecificHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : requestSpecificHeaders.entrySet()) {
                allHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        IRequest request = new DefaultRequest(HttpMethod.GET, href, queryString, allHeaders, null, 0);

        return executeRequest(request, responseType);
    }

    public <T> T delete(String href, Class<T> responseType) throws IdxRestException {
        return delete(href, null /*queryString*/, responseType, null);
    }

    public <T> T delete(String href, Class<T> responseType, HashMap<String, String> requestSpecificHeaders) throws IdxRestException {
        return delete(href, null /*queryString*/, responseType, requestSpecificHeaders);
    }

    /**
     * Executes a delete operation on a Rest service
     * @param href url of the Rest service
     * @param queryString a query string to provide additional information related to the delete
     * @param responseType type the response will be deserialised to
     * @return the deserialised resource returned by the Rest service
     * @throws IdxRestException
     */
    public <T> T delete(String href, QueryString queryString, Class<T> responseType) throws IdxRestException {

        return delete(href, queryString, responseType, null);
    }

    public <T> T delete(String href, QueryString queryString, Class<T> responseType, HashMap<String, String> requestSpecificHeaders) throws IdxRestException {

        if (requestExecutor == null) throw new IllegalStateException("Object not initialised");

        HttpHeaders allHeaders = headers.clone();
        if (requestSpecificHeaders != null && !requestSpecificHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : requestSpecificHeaders.entrySet()) {
                allHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        IRequest request = new DefaultRequest(HttpMethod.DELETE, href, queryString, allHeaders, null, 0);

        return executeRequest(request, responseType);
    }

    public <T> T deleteWithObject(Object obj, String href, Class<T> responseType) throws IdxRestException {

        return deleteWithObject(obj, href, responseType, null);
    }

    /**
     * Executes a delete operation on a Rest service
     * @param href url of the Rest service
     * @param responseType type the response will be deserialised to
     * @return the deserialised resource returned by the Rest service
     * @throws IdxRestException
     */
    public <T> T deleteWithObject(Object obj, String href, Class<T> responseType, HashMap<String, String> requestSpecificHeaders) throws IdxRestException {

        if (requestExecutor == null) throw new IllegalStateException("Object not initialised");

        byte bodyBytes[] = null;
        try {
            bodyBytes = mapper.writeValueAsBytes(obj);
        }
        catch (Exception ex) {
            throw new IdxRestException("Failed to make the REST call: before calling, failed to serialize the passed object to JSON", ex);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bodyBytes);
        int length = bis.available();

        HttpHeaders allHeaders = headers.clone();
        if (requestSpecificHeaders != null && !requestSpecificHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : requestSpecificHeaders.entrySet()) {
                allHeaders.add(entry.getKey(), entry.getValue());
            }
        }

        IRequest request = new DefaultRequest(HttpMethod.DELETE, href, null, allHeaders, bis, length);

        return executeRequest(request, responseType);
    }

    /**
     *
     * @param request
     * @param responseType
     * @return
     * @throws IdxRestException
     */
    public <T> T executeRequest(IRequest request, Class<T> responseType) throws IdxRestException {

        if (request instanceof DefaultRequest) {
            HttpHeaders headers = ((DefaultRequest)request).getHeaders();
            headers.add(Version.sdkVersionHeaderName, Version.sdkVersionValue);
            if (getCookie() != null) {
                headers.add("Cookie", getCookie());
            }
        }

        IResponse response = null;
        IRequestIdentityXWrapper requestWrapper = IdentityxWrapper.getIRequest(request);
        IResponseIdentityXWrapper responseWrapper= null ;
        try {

            httpResponseInterceptor.process(requestWrapper);
            response = requestExecutor.executeRequest(request);
            responseWrapper = IdentityxWrapper.getIResponse(response);
            httpResponseInterceptor.process(requestWrapper, responseWrapper);
        }
        catch (RestException ex) {
            httpResponseInterceptor.exception(requestWrapper, responseWrapper , ex);
            throw ex;
        }
        catch (Exception ex) {
            httpResponseInterceptor.exception(requestWrapper, responseWrapper , ex);
            throw new RestException("Failed to make the REST call", ex);
        }

        if (response == null) {
            RestException ex = new RestException("Failed to make the REST call, no response received");
            httpResponseInterceptor.exception(requestWrapper, responseWrapper , ex);
            throw ex;
        }

        if (response.getHttpStatus() < 200 || response.getHttpStatus() >= 300) {
            IdxRestException idxRestException = null;
            try {
                if (response.getBody().available() > 0) {
                    idxRestException = mapper.readValue(response.getBody(), IdxRestException.class);
                }
                else {
                    idxRestException = new IdxRestException();
                    idxRestException.setHttpStatus(response.getHttpStatus());
                }
            }
            catch (Exception ex) {
                throw new RestException("Failed to make the REST call; Response was " + response.getBody(), ex, response.getHttpStatus());
            }
            throw idxRestException;
        }
        else {
            try {
                if(responseType != null && response.getHeaders().getContentType() != null
                        && response.getHeaders().getContentType().toString().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
                    return mapper.readValue(response.getBody(), responseType);
                }
                else {
                    return null;
                }
            } catch (Exception ex) {
                throw new RestException("REST call executed on the server but failed to map the response to required type", ex, response.getHttpStatus());
            }
        }
    }

    public <T> T list(QueryHolder queryHolder, String href, Class<T> responseType) throws IdxRestException {

        return list(queryHolder, href, responseType, null);
    }

    public <T> T list(QueryHolder queryHolder, String href, Class<T> responseType, HashMap<String, String> requestSpecificHeaders) throws IdxRestException {
        QueryString queryString = null;
		/*
		if (queryHolder == null) {
			queryHolder = new QueryHolder();
		}
		*/

        queryString = new QueryString();
        if (queryHolder != null) {
            if (queryHolder.getExpandSpec() != null) {
                try {
                    queryString.put("expand", getObjectMapper().writeValueAsString(queryHolder.getExpandSpec()));
                } catch (JsonProcessingException e) {
                    throw new IdxRestException("Failed to convert to JSON", e);
                }
            }

            if (queryHolder.getPageSpec() != null) {
                queryString.put("page", Integer.toString(queryHolder.getPageSpec().getPage()));
                queryString.put("limit", Integer.toString(queryHolder.getPageSpec().getLimit()));
            }

            if (queryHolder.getSensitiveDataSpec() != null) {
                queryString.put("sensitiveData", String.valueOf(queryHolder.getSensitiveDataSpec().getSensitiveData()));
            }

            if (queryHolder.isIncludeAuthorizationInfo()) {
                queryString.put("includeAuthorizationInfo", "true");
            }

            if (queryHolder.getSearchSpec() != null) {
                Map<String, String> searchValues = queryHolder.getSearchSpec().buildSearchValues();
                if (searchValues != null) {
                    for (String name : searchValues.keySet()) {
                        queryString.put(name, searchValues.get(name));
                    }
                }
            }

            if (queryHolder.getSortSpec() != null) {
                Map<String, String> sortValues = queryHolder.getSortSpec().buildSearchValues();
                if (sortValues != null) {
                    for (String name : sortValues.keySet()) {
                        queryString.put(name, sortValues.get(name));
                    }
                }
            }
        }

        T result = get(queryString, href, responseType, requestSpecificHeaders);
        return result;
    }


    /**
     *
     * @return
     */
    public IApiKey getApiKey() {
        return apiKey;
    }

    protected void setApiKey(IApiKey apiKey) {
        this.apiKey = apiKey;
    }

    /**
     *
     * @return {@link Proxy} for the httpClientRequestExecutor
     */
    public Proxy getProxy() {
        return proxy;
    }

    /**
     *
     * @param proxy {@link Proxy} for the httpClientRequestExecutor
     */
    protected void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     *
     * @return cookie attached to the request
     */
    public String getCookie() {
        return cookie;
    }

    /**
     *
     * @param cookie to be attached to the request
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public SSLConnectionSocketFactory getSSLConnectionSocketFactory() {
        return sslCSF;
    }

    protected void setSSLConnectionSocketFactory(SSLConnectionSocketFactory sslCSF) {
        this.sslCSF = sslCSF;
    }

}
