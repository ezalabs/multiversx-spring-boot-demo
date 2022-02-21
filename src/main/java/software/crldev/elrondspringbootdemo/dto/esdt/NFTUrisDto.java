package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.NFTAddURI;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.ESDTUri;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

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
