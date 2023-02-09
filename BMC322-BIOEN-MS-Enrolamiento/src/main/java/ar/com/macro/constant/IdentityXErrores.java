package ar.com.macro.constant;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IdentityXErrores.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-12
 */
@Getter
@AllArgsConstructor
public enum IdentityXErrores {
  FACE_NOT_FOUND(748, 75115),
  TOO_MANY_FACES(749, 75116),
  FACE_LIVENESS_IMPROVE_LIGHTING(800, 75126),
  FACE_LIVENESS_COULD_NOT_FIND_EYES(876, 75127),
  FACE_TO_CLOSE(913, 75117),
  FACE_CLOSE_TO_BORDER(914, 75118),
  FACE_CROPPED(915, 75119),
  FACE_TOO_SMALL(916, 75120),
  FACE_ANGLE_TO_LARGE(917, 75121),
  FACE_IS_OCCLUDED(918, 75122),
  OTHER_ERROR(927, 75123),
  COULD_NOT_ASSESS_PASIVE_FACE_LIVENESS(928, 75124),
  PASIVE_FACE_LIVENESS_CHECK_FAILED(929, 75125);
  
  private Integer identityXCode;
  private Integer internalCode;
  
  public static IdentityXErrores getValueOf(final Integer codigo) {
    return Arrays.asList(values())
        .stream()
        .filter(error -> error.getIdentityXCode().equals(codigo))
        .findFirst()
        .orElse(null);
  }
}
