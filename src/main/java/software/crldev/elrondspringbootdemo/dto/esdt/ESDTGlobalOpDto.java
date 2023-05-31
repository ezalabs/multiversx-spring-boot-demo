package software.crldev.elrondspringbootdemo.dto.esdt;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTGlobalOp;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class ESDTGlobalOpDto implements ESDTDto {

  private String type;
  private String targetAddress;
  private String tokenIdentifier;
  private String gasLimit;

  @Override
  public ESDTGlobalOp mapToTransaction() {
    return ESDTGlobalOp.builder()
        .type(processType(type))
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .targetAddress(isNullOrEmpty(targetAddress) ? Address.zero() : Address.fromBech32(targetAddress))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtGlobalOp())
        .build();
  }

  private ESDTGlobalOp.Type processType(String type) {
    return ESDTGlobalOp.Type.valueOf(type.toUpperCase());
  }
}
