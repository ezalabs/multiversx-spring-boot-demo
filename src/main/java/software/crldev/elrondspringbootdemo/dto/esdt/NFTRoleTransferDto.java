package software.crldev.elrondspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.NFTCreationRoleTransfer;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

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
