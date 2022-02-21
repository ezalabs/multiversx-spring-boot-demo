package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTOwnershipTransfer;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class ESDTOwnershipTransferDto implements ESDTDto {

    private String targetAddress;
    private String tokenIdentifier;
    private String gasLimit;

    @Override
    public ESDTOwnershipTransfer mapToTransaction() {
        return ESDTOwnershipTransfer.builder()
                .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
                .targetAddress(isNullOrEmpty(targetAddress) ? Address.zero() : Address.fromBech32(targetAddress))
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtIssuance())
                .build();
    }
}
