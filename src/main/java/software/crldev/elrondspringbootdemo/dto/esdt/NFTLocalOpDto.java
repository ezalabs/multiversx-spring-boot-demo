package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.NFTSFTLocalOp;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import java.math.BigInteger;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class NFTLocalOpDto implements ESDTDto {

    private String type;
    private String tokenIdentifier;
    private Long nonce;
    private BigInteger amount;
    private String gasLimit;

    @Override
    public NFTSFTLocalOp mapToTransaction() {
        return NFTSFTLocalOp.builder()
                .type(processType(type))
                .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
                .nonce(Nonce.fromLong(nonce))
                .amount(Balance.fromNumber(amount))
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtLocalOp())
                .build();
    }

    private NFTSFTLocalOp.Type processType(String type) {
        return NFTSFTLocalOp.Type.valueOf(type.toUpperCase());
    }
}
