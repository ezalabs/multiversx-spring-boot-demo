package io.ezalabs.multiversxspringbootdemo.dto;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

@Data
@NoArgsConstructor
public class TransactionDto {

  private String receiverAddress;
  private Double value;
  private String data;
  private String gasLimit;

  public TransactionRequest mapToRequest() {
    return TransactionRequest.builder()
        .receiverAddress(Address.fromBech32(receiverAddress))
        .value(Balance.fromEgld(value))
        .data(nonNull(data) ? PayloadData.fromString(data) : PayloadData.empty())
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.zero())
        .build();
  }

}
