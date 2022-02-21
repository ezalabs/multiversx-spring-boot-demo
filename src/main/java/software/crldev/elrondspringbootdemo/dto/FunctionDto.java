package software.crldev.elrondspringbootdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionArgs;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class FunctionDto {

    private String scAddress;
    private Double value;
    private String funcName;
    private String gasLimit;
    private String[] args;

    public ContractQuery mapQuery(Wallet wallet) {
        return ContractQuery.builder()
                .callerAddress(Address.fromHex(wallet.getPublicKeyHex()))
                .smartContractAddress(Address.fromBech32(scAddress))
                .functionName(FunctionName.fromString(funcName))
                .args(Arrays.asList(args).isEmpty() ? FunctionArgs.empty() : FunctionArgs.fromString(args))
                .build();
    }

    public ContractFunction mapFunction() {
        return ContractFunction.builder()
                .smartContractAddress(Address.fromBech32(scAddress))
                .functionName(FunctionName.fromString(funcName))
                .args(Arrays.asList(args).isEmpty() ? FunctionArgs.empty() : FunctionArgs.fromString(args))
                .value(nonNull(value) ? Balance.fromString(gasLimit) : Balance.zero())
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultSmartContractCall())
                .build();
    }

}
