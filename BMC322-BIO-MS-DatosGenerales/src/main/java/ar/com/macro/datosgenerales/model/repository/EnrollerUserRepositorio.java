package ar.com.macro.datosgenerales.model.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ar.com.macro.datosgenerales.model.repository.entity.EnrollerUser;

public interface EnrollerUserRepositorio
    extends JpaRepository<EnrollerUser, Long>, JpaSpecificationExecutor<EnrollerUser> {

  Optional<EnrollerUser> findByUserId(String userId);

  @Query(
      "SELECT e FROM EnrollerUser e "
          + "WHERE e.documentNumber = :docNumber "
          + "AND (:docType is null OR e.documentType = :docType) "
          + "AND (:gender is null OR e.gender = :gender)")
  List<EnrollerUser> getEnrollerUser(String docType, String docNumber, String gender, Pageable page);

  @Query("SELECT DISTINCT(o.branchOffice) FROM EnrollerUser o WHERE o.status IN (:status)")
  List<String> getBranchOfficeWithEnrollerUserInState(List<String> status, Sort sort);
}
