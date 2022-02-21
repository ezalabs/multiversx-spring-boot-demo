package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.NFTAttributesUpdate;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenAttributes;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import static java.util.Objects.nonNull;

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
