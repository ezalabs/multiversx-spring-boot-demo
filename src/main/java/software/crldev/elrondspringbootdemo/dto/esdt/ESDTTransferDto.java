package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransfer;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import java.math.BigInteger;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.nonNull;
import static software.crldev.elrondspringbootdemo.dto.ArgUtils.processArgs;

@Data
@NoArgsConstructor
public class ESDTTransferDto implements ESDTDto {

    private String receiverAddress;
    private String tokenIdentifier;
    private BigInteger amount;
    private String functionName;
    private Object[] functionArgs;
    private String gasLimit;


    @Override
    public ESDTTransfer mapToTransaction() {
        return ESDTTransfer.builder()
                .receiverAddress(Address.fromBech32(receiverAddress))
                .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
                .amount(Balance.fromNumber(amount))
                .functionName(isNullOrEmpty(functionName) ? FunctionName.empty() : FunctionName.fromString(functionName))
                .args(processArgs(functionArgs))
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtTransfer())
                .build();
    }

}
