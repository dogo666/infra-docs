package ar.com.macro.validacion.model.client.daonengine.header;

import lombok.AllArgsConstructor;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapMessage;

@AllArgsConstructor
public class SoapRequestHeaderModifier implements WebServiceMessageCallback {

    private String soapAction;

    @Override
    public void doWithMessage(WebServiceMessage message) {
        ((SoapMessage)message).setSoapAction(soapAction);
    }

}