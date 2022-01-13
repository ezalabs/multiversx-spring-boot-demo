package software.crldev.elrondspringbootdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionArgs;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScQuery;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class FunctionDto {

    private String scAddress;
    private Double value;
    private String funcName;
    private String[] args;

    public ScQuery mapQuery(Wallet wallet) {
        return ScQuery.builder()
                .callerAddress(Address.fromHex(wallet.getPublicKeyHex()))
                .smartContractAddress(Address.fromBech32(this.scAddress))
                .functionName(FunctionName.fromString(this.funcName))
                .args(Arrays.asList(this.args).isEmpty() ? FunctionArgs.empty() : FunctionArgs.fromString(args))
                .value(nonNull(this.value) ? Balance.fromEgld(this.value) : Balance.zero())
                .build();
    }

    public ScFunction mapFunction() {
        return ScFunction.builder()
                .smartContractAddress(Address.fromBech32(this.scAddress))
                .functionName(FunctionName.fromString(this.funcName))
                .args(Arrays.asList(this.args).isEmpty() ? FunctionArgs.empty() : FunctionArgs.fromString(args))
                .value(nonNull(this.value) ? Balance.fromEgld(this.value) : Balance.zero())
                .build();
    }

}
