package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.NFTCreationRoleTransfer;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class NFTRoleTransferDto implements ESDTDto {

    private String tokenIdentifier;
    private String fromAddress;
    private String toAddress;
    private String gasLimit;

    @Override
    public NFTCreationRoleTransfer mapToTransaction() {
        return NFTCreationRoleTransfer.builder()
                .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
                .fromAddress(Address.fromBech32(fromAddress))
                .toAddress(Address.fromBech32(toAddress))
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtIssuance())
                .build();
    }
}
