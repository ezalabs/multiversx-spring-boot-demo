package software.crldev.elrondspringbootdemo.dto;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.FunctionArg;

public class ArgUtils {

  public static List<FunctionArg> processArgs(Object[] functionArgs) {
    return isEmpty(functionArgs)
        ? new ArrayList<>()
        : Arrays.stream(functionArgs)
            .map(a -> {
              if (a instanceof String) {
                return FunctionArg.fromString((String) a);
              } else if (a instanceof Integer) {
                return FunctionArg.fromNumber(BigInteger.valueOf((Integer) a));
              } else {
                throw new IllegalArgumentException("Argument must be of type string or number");
              }
            })
            .collect(Collectors.toList());
  }

}
