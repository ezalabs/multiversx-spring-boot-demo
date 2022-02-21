package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.NFTSFTGlobalOp;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import static java.util.Objects.nonNull;

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
