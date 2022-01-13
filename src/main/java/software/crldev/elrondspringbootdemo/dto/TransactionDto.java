package software.crldev.elrondspringbootdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class TransactionDto {

    private String receiverAddress;
    private Double value;
    private String data;
    private String gasLimit;

    public TransactionRequest mapToRequest() {
        return TransactionRequest.builder()
                .receiverAddress(Address.fromBech32(this.receiverAddress))
                .value(Balance.fromEgld(this.value))
                .data(nonNull(this.data) ? PayloadData.fromString(this.data) : PayloadData.empty())
                .gasLimit(nonNull(this.gasLimit) ? GasLimit.fromString(this.gasLimit) : null)
                .build();
    }

}
