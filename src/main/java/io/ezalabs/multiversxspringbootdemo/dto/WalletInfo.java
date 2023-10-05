package io.ezalabs.multiversxspringbootdemo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletInfo {

  private String address;
  private List<String> mnemonicPhrase;

}
