package ar.com.macro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import static ar.com.macro.commons.values.constants.comm.HeaderConstants.HEADER_AUTHORIZATION;

@Profile("swagger")
@Configuration
public class OpenAPISwaggerConfig {

  @Bean
  public OpenAPI macroOpenAPI() {

    var contact = new Contact();
    contact.setName("Globant");
    contact.setUrl("https://www.globant.com");
    contact.setEmail("macro-team@globant.com");

    return new OpenAPI()
        .components(
            new Components()
                .addParameters(
                    "X-Application-Id",
                    new Parameter()
                        .name("X-Application-Id")
                        .in("header")
                        .description(
                            "Header que identifica a la aplicación cliente que consume el servicio")
                        .required(true))
                .addSecuritySchemes(
                    HEADER_AUTHORIZATION,
                    new SecurityScheme()
                        .type(Type.APIKEY)
                        .name(HEADER_AUTHORIZATION)
                        .in(In.HEADER)))
        .info(
            new Info()
                .title("Banco Macro")
                .description("API servicios Banco Macro")
                .version("1.0.0")
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0"))
                .contact(contact));
  }
}
