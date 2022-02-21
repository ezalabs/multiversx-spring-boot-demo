package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.NFTCreation;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.*;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Hash;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class NFTCreateDto implements ESDTDto {

    private String tokenIdentifier;
    private BigInteger initialQuantity;
    private String nftName;
    private Integer royalties;
    private String hash;
    private String metadataUri;
    private String[] tags;
    private List<String> uris;
    private String gasLimit;

    @Override
    public NFTCreation mapToTransaction() {
        return NFTCreation.builder()
                .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
                .initialQuantity(TokenInitialSupply.fromNumber(initialQuantity))
                .nftName(TokenName.fromString(nftName))
                .tokenRoyalties(TokenRoyalties.fromNumber(royalties))
                .hash(nonNull(hash) ? Hash.fromString(hash) : Hash.empty())
                .tokenAttributes(TokenAttributes.fromString(metadataUri, tags))
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultNftCreate())
                .uris(uris.stream()
                        .map(e -> ESDTUri.fromString(e, ESDTUri.Type.MEDIA))
                        .collect(Collectors.toSet()))
                .build();
    }

}
