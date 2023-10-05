package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.NFTSFTGlobalOp;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class NFTGlobalOpDto implements ESDTDto {

  private String type;
  private String tokenIdentifier;
  private Long nonce;
  private String address;
  private String gasLimit;

  @Override
  public NFTSFTGlobalOp mapToTransaction() {
    return NFTSFTGlobalOp.builder()
        .type(processType(type))
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .nonce(Nonce.fromLong(nonce))
        .address(Address.fromBech32(address))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtGlobalOp())
        .build();
  }

  private NFTSFTGlobalOp.Type processType(String type) {
    return NFTSFTGlobalOp.Type.valueOf(type.toUpperCase());
  }
}
