package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.nonNull;
import static io.ezalabs.multiversxspringbootdemo.dto.ArgUtils.processArgs;

import java.math.BigInteger;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTTransfer;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class ESDTTransferDto implements ESDTDto {

  private String receiverAddress;
  private String tokenIdentifier;
  private BigInteger amount;
  private String functionName;
  private Object[] functionArgs;
  private String gasLimit;


  @Override
  public ESDTTransfer mapToTransaction() {
    return ESDTTransfer.builder()
        .receiverAddress(Address.fromBech32(receiverAddress))
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .amount(Balance.fromNumber(amount))
        .functionName(isNullOrEmpty(functionName) ? FunctionName.empty() : FunctionName.fromString(functionName))
        .args(processArgs(functionArgs))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtTransfer())
        .build();
  }

}
