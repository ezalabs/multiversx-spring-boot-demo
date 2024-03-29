package io.ezalabs.multiversxspringbootdemo.dto;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractFunction;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;

@Data
@NoArgsConstructor
public class FunctionDto {

  private String scAddress;
  private Double value;
  private String funcName;
  private String gasLimit;
  private Object[] args;

  public ContractQuery mapQuery(Wallet wallet) {
    return ContractQuery.builder()
        .callerAddress(Address.fromHex(wallet.getPublicKeyHex()))
        .smartContractAddress(Address.fromBech32(scAddress))
        .functionName(FunctionName.fromString(funcName))
        .args(ArgUtils.processArgs(args))
        .build();
  }

  public ContractFunction mapFunction() {
    return ContractFunction.builder()
        .smartContractAddress(Address.fromBech32(scAddress))
        .functionName(FunctionName.fromString(funcName))
        .args(ArgUtils.processArgs(args))
        .value(nonNull(value) ? Balance.fromEgld(value) : Balance.zero())
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultSmartContractCall())
        .build();
  }

}
