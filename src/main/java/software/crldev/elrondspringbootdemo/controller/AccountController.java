package software.crldev.elrondspringbootdemo.controller;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootdemo.dto.WalletInfo;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountBalance;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountNonce;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountOnNetwork;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountStorageValue;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionForAddress;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Account;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.TransactionStatus;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.TransactionVersion;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.account.MxAccountInteractor;
import software.crldev.multiversxspringbootstarterreactive.util.MnemonicsUtils;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
@Slf4j
public class AccountController {

  @Autowired
  private final MxAccountInteractor accountInteractor;

  @PostMapping("/wallet")
  WalletInfo createWallet() {
    var mnemonic = MnemonicsUtils.generateMnemonic();
    var wallet = Wallet.fromMnemonic(mnemonic, 0);

    var bech32Address = Address.fromHex(wallet.getPublicKeyHex()).getBech32();

    return new WalletInfo(bech32Address, mnemonic);
  }


  @GetMapping("/{address}")
  Mono<AccountOnNetwork> getAccountInfo(@PathVariable String address) {
    return accountInteractor.getAccountInfo(Address.fromBech32(address))
        .doOnSuccess(r -> {
          // AccountOnNetwork is an API response which can also be converted to domain object

          Account account = r.toDomainObject();
          Address addrss = account.getAddress();
          Balance balance = account.getBalance();
          Nonce nonce = account.getNonce();

        });
  }

  @GetMapping("/{address}/balance")
  Mono<AccountBalance> getAccountBalance(@PathVariable String address) {
    return accountInteractor.getBalance(Address.fromBech32(address));
  }

  @GetMapping("/{address}/nonce")
  Mono<AccountNonce> getAccountNonce(@PathVariable String address) {
    return accountInteractor.getNonce(Address.fromBech32(address));
  }

  @GetMapping("/{address}/transactions")
  Mono<List<TransactionForAddress>> getTransactions(@PathVariable String address) {
    return accountInteractor.getTransactions(Address.fromBech32(address))
        .doOnSuccess(r -> {
          // TransactionForAddress is an API response which can also be converted to domain object

          if (!r.isEmpty()) {
            Transaction transaction = r.get(0).toDomainObject();
            Address senderAddress = transaction.getSender();
            Address receiverAddress = transaction.getReceiver();
            Balance balance = transaction.getValue();
            Nonce nonce = transaction.getNonce();
            PayloadData data = transaction.getPayloadData();
            GasLimit gasLimit = transaction.getGasLimit();
            TransactionStatus status = transaction.getStatus();
            TransactionVersion version = transaction.getVersion();

            var egldValue = balance.toCurrencyString(); // ex: 50.23EGLD
            var encodedData = data.encoded();

            // ... and others
          }
        });
  }

  @GetMapping("/{address}/key/{key}")
  Mono<AccountStorageValue> getStorageValue(@PathVariable String address, @PathVariable String key) {
    return accountInteractor.getStorageValue(Address.fromBech32(address), key);
  }

  @GetMapping("/{address}/keys")
  Mono<Map<String, String>> getStorage(@PathVariable String address) {
    return accountInteractor.getStorage(Address.fromBech32(address));
  }

}
