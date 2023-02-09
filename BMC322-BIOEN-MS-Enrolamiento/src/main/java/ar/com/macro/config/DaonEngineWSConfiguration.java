package ar.com.macro.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import lombok.AllArgsConstructor;

@EnableWs
@Configuration
public class DaonEngineWSConfiguration {

	@Value("${daonengine.user}")
	private String userName;

	@Value("${daonengine.password}")
	private String userPassword;

	@Value("${daonengine.uri}")
	private String uri;

	@Value("${daonengine.context}")
	private String context;

	@Value("${daonengine.mtls.actived:false}")
	private Boolean mtlsActived;

	@Autowired
	HttpComponentsMessageSender mtlsMessageSender;

	@Bean("daonEngineMarshaller")
	public Jaxb2Marshaller daonEngineMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(context);
		return marshaller;
	}

	@Bean("daonEngineWebServiceTemplate")
	public WebServiceTemplate daonEngineWebServiceTemplate() {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(daonEngineMarshaller());
		webServiceTemplate.setUnmarshaller(daonEngineMarshaller());
		webServiceTemplate.setDefaultUri(uri);
		webServiceTemplate.setMessageSender(httpUrlConnectionMessageSender());
		return webServiceTemplate;
	}

	private WebServiceMessageSender httpUrlConnectionMessageSender(){
		return mtlsActived ? mtlsMessageSender : CustomWSMessageSender.of(userName, userPassword);
	}
	
}

@AllArgsConstructor(staticName = "of")
class CustomWSMessageSender extends HttpUrlConnectionMessageSender {

  private static final String ACCEPT_ENCODING = "Accept-Encoding";
  private static final String BASIC = "Basic ";
  private static final String AUTHORIZATION = "Authorization";
  private String userName;
  private String password;

  @Override
  protected void prepareConnection(HttpURLConnection connection) throws IOException {

    var basicValue = new String(Base64.encodeBase64((userName + ":" + password).getBytes()));
    connection.setRequestProperty(AUTHORIZATION, BASIC + basicValue);
    connection.setRequestProperty(ACCEPT_ENCODING, "gzip,deflate");
    super.prepareConnection(connection);
  }
}
