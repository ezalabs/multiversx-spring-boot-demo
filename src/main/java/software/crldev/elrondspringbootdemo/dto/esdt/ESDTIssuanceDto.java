package software.crldev.elrondspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTIssuance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenDecimals;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenInitialSupply;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenName;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenProperty;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenPropertyName;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenTicker;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

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
