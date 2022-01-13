package software.crldev.elrondspringbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WalletInfo {

    private String address;
    private List<String> mnemonicPhrase;

}
