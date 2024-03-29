package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTNFTMultiTransfer;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TransferToken;

@Data
@NoArgsConstructor
public class ESDTMultiTransferDto implements ESDTDto {

  private String receiverAddress;
  private List<TokenTransferDto> tokens;

  @Override
  public ESDTNFTMultiTransfer mapToTransaction() {
    return ESDTNFTMultiTransfer.builder()
        .receiverAddress(Address.fromBech32(receiverAddress))
        .tokenList(tokens.stream()
            .map(t -> TransferToken.builder()
                .identifier(TokenIdentifier.fromString(t.tokenIdentifier))
                .nonce(Nonce.fromLong(t.nonce))
                .amount(Balance.fromNumber(t.amount))
                .build()).collect(Collectors.toList()))
        .build();
  }

  @Data
  @NoArgsConstructor
  public static class TokenTransferDto {

    private String tokenIdentifier;
    private Long nonce;
    private BigInteger amount;

  }

}
