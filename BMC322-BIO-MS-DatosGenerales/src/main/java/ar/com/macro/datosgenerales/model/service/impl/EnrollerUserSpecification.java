package ar.com.macro.datosgenerales.model.service.impl;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import ar.com.macro.datosgenerales.model.repository.entity.EnrollerUser;

/**
 * EnrollerUserSpecification.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-20
 */
public final class EnrollerUserSpecification {
  
  public static final String STATUS = "status";
  public static final String BRANCH_OFFICE = "branchOffice";

  private EnrollerUserSpecification() {
    super();
  }
  
  public static Specification<EnrollerUser> emptySpecification() {
    return (enrollerUser, cq, cb) -> cb.isTrue(cb.literal(true));
  }
  
  public static Specification<EnrollerUser> inStatus(final List<String> status) {
    return (enrollerUser, cq, cb) -> enrollerUser.get(STATUS).in(status);
  }

  public static Specification<EnrollerUser> inBranchOffice(final List<String> branchOffices) {
    return (enrollerUser, cq, cb) -> enrollerUser.get(BRANCH_OFFICE).in(branchOffices);
  }
  
}
