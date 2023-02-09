package ar.com.macro.utils;

import ar.com.macro.biometria.commons.trace.util.outbound.identityx.IRequestIdentityXWrapper;
import ar.com.macro.biometria.commons.trace.util.outbound.identityx.IResponseIdentityXWrapper;
import com.identityx.auth.def.IRequest;
import com.identityx.auth.def.IResponse;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.identityx.auth.impl.DigestResponseAuthenticator.getResponsePayload;

public class IdentityxWrapper {

    public static IRequestIdentityXWrapper getIRequest(IRequest request){

        return new IRequestIdentityXWrapper(){

            @Override
            public List<String> getHeaders() {
                return request.getHeaders().keySet().stream()
                        .map((h)-> h + " : " + request.getHeaders().get(h))
                        .collect(Collectors.toList());
            }

            @Override
            public String getMethod() {
                return request.getMethod().name();
            }

            @Override
            public URI getResourceUrl() {
                return request.getResourceUrl();
            }

            @Override
            public String getQueryString() {
                return request.getQueryString().toString();
            }

            @Override
            public InputStream getBody() {
                return request.getBody();
            }
            @Override
            public void setBody(InputStream inputStream, long var2){
                request.setBody(inputStream,var2 );
            }
        };
    }

    public static IResponseIdentityXWrapper getIResponse(IResponse response){

        return new IResponseIdentityXWrapper(){

            @Override
            public List<String> getHeaders() {
                return response.getHeaders().keySet().stream()
                        .map((h)-> h + " : " + response.getHeaders().get(h))
                        .collect(Collectors.toList());
            }
            @Override
            public int getHttpStatus() {
                return response.getHttpStatus();
            }

            @Override
            public String getBody(){
                return getResponsePayload(response);
            }

        };
    }
}

