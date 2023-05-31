package software.crldev.elrondspringbootdemo.dto.esdt;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTOwnershipTransfer;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class ESDTOwnershipTransferDto implements ESDTDto {

  private String targetAddress;
  private String tokenIdentifier;
  private String gasLimit;

  @Override
  public ESDTOwnershipTransfer mapToTransaction() {
    return ESDTOwnershipTransfer.builder()
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .targetAddress(isNullOrEmpty(targetAddress) ? Address.zero() : Address.fromBech32(targetAddress))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtIssuance())
        .build();
  }
}
