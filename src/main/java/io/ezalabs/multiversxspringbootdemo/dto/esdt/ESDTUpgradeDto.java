package io.ezalabs.multiversxspringbootdemo.dto.esdt;

import static java.util.Objects.nonNull;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTUpgrade;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenProperty;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenPropertyName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;

@Data
@NoArgsConstructor
public class ESDTUpgradeDto implements ESDTDto {

  private String tokenIdentifier;
  private String gasLimit;
  private Map<String, Boolean> properties;

  @Override
  public ESDTUpgrade mapToTransaction() {
    return ESDTUpgrade.builder()
        .tokenIdentifier(TokenIdentifier.fromString(tokenIdentifier))
        .gasLimit(nonNull(gasLimit) ? GasLimit.fromString(gasLimit) : GasLimit.defaultEsdtIssuance())
        .properties(properties.entrySet().stream()
            .map(e -> new TokenProperty(TokenPropertyName.getByValue(e.getKey()), e.getValue()))
            .collect(Collectors.toSet())
        )
        .build();
  }

}
