package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.NFTAttributesUpdate;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenAttributes;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class NFTAttributesDto implements ESDTDto {

  private String tokenIdentifier;
  private String metadataUri;
  private long nonce;
  private String[] tags;
  private String gasLimit;

  @Override
  public NFTAttributesUpdate mapToTransaction() {
    return NFTAttributesUpdate.builder()
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .nonce(Nonce.fromLong(nonce))
        .tokenAttributes(TokenAttributes.fromString(metadataUri, tags))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultNftCreate())
        .build();
  }

}
