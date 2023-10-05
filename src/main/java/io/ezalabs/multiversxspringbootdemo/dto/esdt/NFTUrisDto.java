package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.NFTAddURI;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTUri;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class NFTUrisDto implements ESDTDto {

  private String tokenIdentifier;
  private String metadataUri;
  private long nonce;
  private List<String> uris;
  private String gasLimit;

  @Override
  public NFTAddURI mapToTransaction() {
    return NFTAddURI.builder()
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .nonce(Nonce.fromLong(nonce))
        .uris(uris.stream()
            .map(e -> ESDTUri.fromString(e, ESDTUri.Type.MEDIA))
            .collect(Collectors.toSet()))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultNftCreate())
        .build();
  }

}
