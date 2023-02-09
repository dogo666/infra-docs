package ar.com.macro.validacion.model.client.identityx.rest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Results extends BaseIdentityXDTO{
    Item[] items;
}
