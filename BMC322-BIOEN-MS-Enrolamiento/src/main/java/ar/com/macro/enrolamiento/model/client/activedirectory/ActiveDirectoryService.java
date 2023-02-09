package ar.com.macro.enrolamiento.model.client.activedirectory;

/**
 * ActiveDirectoryService.
 *
 * @author globant
 * @version 1.0
 * @since 2022-07-18
 */
public interface ActiveDirectoryService {

  /**
   * Servicio que permite obtener el token de acceso para consumir servicios del active directory.
   *
   * @return token de acceso
   */
  String getAccessToken();

  /**
   * Permite validar frente al Active Directory si un usuario hace parte de un rol especifico.
   *
   * @param email el email del usuario que requiere ser validado
   * @param group el rol o grupo dentro del active directory
   * @param token el token de acceso
   * @return true indicando que el usuario hace parte del grupo, false en caso contrario
   */
  boolean isUserInGroup(final String email, String group, String token);
}
