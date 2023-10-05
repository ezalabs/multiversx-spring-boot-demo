package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.NFTCreationRoleTransfer;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class NFTRoleTransferDto implements ESDTDto {

  private String tokenIdentifier;
  private String fromAddress;
  private String toAddress;
  private String gasLimit;

  @Override
  public NFTCreationRoleTransfer mapToTransaction() {
    return NFTCreationRoleTransfer.builder()
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .fromAddress(Address.fromBech32(fromAddress))
        .toAddress(Address.fromBech32(toAddress))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtIssuance())
        .build();
  }
}
