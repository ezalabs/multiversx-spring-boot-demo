package software.crldev.elrondspringbootdemo.dto.esdt;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTIssuance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.*;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
public class ESDTIssuanceDto implements ESDTDto {

    private String type;
    private Double value;
    private String tokenName;
    private String tokenTicker;
    private BigInteger tokenInitialSupply;
    private Integer tokenDecimals;
    private String gasLimit;
    private Map<String, Boolean> properties;

    @Override
    public ESDTIssuance mapToTransaction() {
        return ESDTIssuance.builder()
                .type(processType(type))
                .tokenName(TokenName.fromString(tokenName))
                .tokenTicker(TokenTicker.fromString(tokenTicker))
                .initialSupply(TokenInitialSupply.fromNumber(tokenInitialSupply))
                .decimals(TokenDecimals.fromNumber(tokenDecimals))
                .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtIssuance())
                .properties(properties.entrySet().stream()
                        .map(e -> new TokenProperty(TokenPropertyName.getByValue(e.getKey()), e.getValue()))
                        .collect(Collectors.toSet()))
                .build();
    }

    private ESDTIssuance.Type processType(String type) {
        return ESDTIssuance.Type.valueOf(type.toUpperCase());
    }

}
