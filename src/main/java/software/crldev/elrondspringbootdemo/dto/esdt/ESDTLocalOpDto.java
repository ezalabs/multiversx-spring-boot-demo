package software.crldev.elrondspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import java.math.BigInteger;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTLocalOp;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class ESDTLocalOpDto implements ESDTDto {

  private String type;
  private String tokenIdentifier;
  private BigInteger amount;
  private String gasLimit;

  @Override
  public ESDTLocalOp mapToTransaction() {
    return ESDTLocalOp.builder()
        .type(processType(type))
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .amount(Balance.fromNumber(amount))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtLocalOp())
        .build();
  }

  private ESDTLocalOp.Type processType(String type) {
    return ESDTLocalOp.Type.valueOf(type.toUpperCase());
  }
}
