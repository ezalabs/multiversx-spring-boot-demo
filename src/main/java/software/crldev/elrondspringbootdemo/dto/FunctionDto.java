package software.crldev.elrondspringbootdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class FunctionDto {

    private String scAddress;
    private Double value;
    private String funcName;
    private String gasLimit;
    private Object[] args;

    public ContractQuery mapQuery(Wallet wallet) {
        return ContractQuery.builder()
                .callerAddress(Address.fromHex(wallet.getPublicKeyHex()))
                .smartContractAddress(Address.fromBech32(scAddress))
                .functionName(FunctionName.fromString(funcName))
                .args(ArgUtils.processArgs(args))
                .build();
    }

    public ContractFunction mapFunction() {
        return ContractFunction.builder()
                .smartContractAddress(Address.fromBech32(scAddress))
                .functionName(FunctionName.fromString(funcName))
                .args(ArgUtils.processArgs(args))
                .value(nonNull(value) ? Balance.fromEgld(value) : Balance.zero())
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultSmartContractCall())
                .build();
    }

}
