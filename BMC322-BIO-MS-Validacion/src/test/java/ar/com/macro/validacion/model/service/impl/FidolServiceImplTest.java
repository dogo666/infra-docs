package ar.com.macro.validacion.model.service.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;

import ar.com.macro.validacion.model.service.PerfilService;
import com.daon.identityx.rest.model.pojo.Authenticator;
import com.daon.identityx.rest.model.pojo.RegistrationChallenge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;
import com.daon.identityx.rest.model.pojo.AuthenticationRequest;
import com.daon.identityx.rest.model.pojo.User;
import ar.com.macro.exceptions.IdentityXException;
import ar.com.macro.validacion.model.client.identityx.IdentityXClient;
import ar.com.macro.validacion.model.service.DatosGeneralesService;

@RunWith(SpringRunner.class)
public class FidolServiceImplTest {

  @Value("${daonengine.nombre.parametros.datos.generales:macro-validacion}")
  private String nombreMicroServicioDatosGenerales;

  @Value(
      "${identityx.nombre.parametros.configuracion.aplicacion:configuracion-aplicacion-identity-x}")
  private String parametroConfigAplicacionIdentityX;

  @Value(
      "${identityx.nombre.parametros.configuracion.fido.politica.reg:configuracion-fido-politica-reg-identity-x}")
  private String parametroConfigFidoPoliticaRegIdentityX;

  @Value(
      "${identityx.nombre.parametros.configuracion.fido.politica.auth:configuracion-fido-politica-auth-identity-x}")
  private String parametroConfigFidoPoliticaAuthIdentityX;

  @Mock private IdentityXClient identityXClient;

  @Mock private DatosGeneralesService datosGeneralesService;

  @Mock private PerfilService perfilService;

  private FidolServiceImpl fidolServiceImpl;

  @Before
  public void init() {
    fidolServiceImpl =
            new FidolServiceImpl(
                    nombreMicroServicioDatosGenerales,
                    parametroConfigAplicacionIdentityX,
                    parametroConfigFidoPoliticaRegIdentityX,
                    parametroConfigFidoPoliticaAuthIdentityX,
                    identityXClient,
                    datosGeneralesService,
                    perfilService);
  }

  @Test
  public void getAuthenticationRequestExito() throws Exception {
    var user = new User();
    user.setUserId("pomin.chuang.85@gmail.com");
    user.setHref("pomin.chuang.85@gmail.com");

    var authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setUser(user);

    when(identityXClient.getAuthenticationRequestRepo(anyString()))
        .thenReturn(Optional.of(authenticationRequest));
    when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(user);

    fidolServiceImpl.getAuthenticationRequestByUser(user.getUserId(), "QTAzLo2wid-8pgP_BOVFP1RUSQ");

    verify(identityXClient).getAuthenticationRequestRepo(anyString());
  }

  @Test(expected = IdentityXException.class)
  public void getAuthenticationRequestUsuarioNoCorresponde() throws Exception {
    var user = new User();
    user.setHref("pomin.chuang.85@gmail.com");

    var authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setUser(user);

    when(identityXClient.getAuthenticationRequestRepo(anyString()))
        .thenReturn(Optional.of(authenticationRequest));
    when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(new User("user@xxx.com"));
    
    fidolServiceImpl.getAuthenticationRequestByUser("user@xxx.com", "QTAzLo2wid-8pgP_BOVFP1RUSQ");
  }

  @Test(expected = IdentityXException.class)
  public void getAuthenticationRequestNoEncontrada() throws Exception {
    when(identityXClient.getAuthenticationRequestRepo(anyString())).thenReturn(Optional.empty());
    
    fidolServiceImpl.getAuthenticationRequestByUser("pomin.chuang.85@gmail.com", "QTAzLo2wid-8pgP_BOVFP1RUSQ");
  }

  @Test(expected = IdentityXException.class)
  public void getAuthenticationRequestErrorInesperado() throws Exception {
    when(identityXClient.getAuthenticationRequestRepo(anyString()))
        .thenThrow(NullPointerException.class);
    
    fidolServiceImpl.getAuthenticationRequestByUser("pomin.chuang.85@gmail.com", "QTAzLo2wid-8pgP_BOVFP1RUSQ");
  }

  @Test(expected = IdentityXException.class)
  public void getAutenticadoresRequestUsuarioNoCorresponde() throws Exception {
    var user = new User();
    user.setHref("pomin.chuang.85@gmail.com");

    var autenticadores = new Authenticator[] {};

    when(identityXClient.getAuthenticators(anyString()))
            .thenReturn(Optional.of(autenticadores));

    when(identityXClient.obtenerRegistroUsuario(anyString())).thenReturn(new User("user@xxx.com"));

    fidolServiceImpl.getAutenticadores("QTAzLo2wid-8pgP_BOVFP1RUSQ");
  }

  @Test(expected = IdentityXException.class)
  public void getAutenticadoresRequestErrorInesperado() throws Exception {
    when(identityXClient.getAuthenticators(anyString()))
            .thenThrow(NullPointerException.class);

    fidolServiceImpl.getAutenticadores("QTAzLo2wid-8pgP_BOVFP1RUSQ");
  }

  @Test
  public void getregistracionRequestExito() throws Exception {

    var registrationChallenge = new RegistrationChallenge();

    when(identityXClient.getRegistrationChallengeById(anyString()))
            .thenReturn(Optional.of(registrationChallenge));

    fidolServiceImpl.consultarRegistracion( "QTAzLo2wid-8pgP_BOVFP1RUSQ");

  }

  @Test(expected = IdentityXException.class)
  public void getregistracionRequestErrorInesperado() throws Exception {
    when(identityXClient.getRegistrationChallengeById(anyString()))
            .thenThrow(NullPointerException.class);
    fidolServiceImpl.consultarRegistracion("QTAzLo2wid-8pgP_BOVFP1RUSQ");
  }

}
