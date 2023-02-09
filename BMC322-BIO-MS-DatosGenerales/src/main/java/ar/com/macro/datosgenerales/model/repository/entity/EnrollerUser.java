package ar.com.macro.datosgenerales.model.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrolleruser")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollerUser implements Serializable {
    private static final long serialVersionUID = 838576650694910254L;

    @Id
    @Column(name = "userid")
    String userId;

    @Column(name = "documenttype")
    @NotNull
    String documentType;

    @Column(name = "documentnumber")
    @NotNull
    String documentNumber;

    @Column(name = "gender")
    @NotNull
    String gender;

    @Column(name = "branchoffice")
    @NotNull
    String branchOffice;

    @Column(name = "creationdate")
    @NotNull
    LocalDateTime creationDate;

    @Column(name = "modificationdate")
    LocalDateTime modificationDate;

    @Column(name = "idcobis")
    String idcobis;

    @Column(name = "status")
    String status;

    @Column(name = "numfingerprints")
    Integer numfingerprints;

    @Column(name = "reason")
    String reason;

    @Column(name = "supervisor")
    String supervisor;

}
