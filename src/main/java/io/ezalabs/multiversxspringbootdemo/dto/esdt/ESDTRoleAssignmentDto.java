package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTRoleAssignment;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;

@Data
@NoArgsConstructor
public class ESDTRoleAssignmentDto implements ESDTDto {

  private String type;
  private String tokenIdentifier;
  private String address;
  private Set<String> roles;

  @Override
  public ESDTRoleAssignment mapToTransaction() {
    return ESDTRoleAssignment.builder()
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .address(Address.fromBech32(address))
        .type(processType(type))
        .roles(roles.stream().map(ESDTSpecialRole::getByValue).collect(Collectors.toSet()))
        .build();
  }

  private ESDTRoleAssignment.Type processType(String type) {
    return ESDTRoleAssignment.Type.valueOf(type.toUpperCase());
  }
}
