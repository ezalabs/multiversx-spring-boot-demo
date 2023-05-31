package software.crldev.elrondspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.NFTAttributesUpdate;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenAttributes;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

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
