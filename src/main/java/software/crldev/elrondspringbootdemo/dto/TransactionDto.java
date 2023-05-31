package software.crldev.elrondspringbootdemo.dto;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

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
