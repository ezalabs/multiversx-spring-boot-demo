package software.crldev.elrondspringbootdemo.controller;


import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootdemo.dto.TransactionDto;
import software.crldev.multiversxspringbootstarterreactive.api.model.SimulationResults;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionCostEstimation;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionOnNetwork;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionStatus;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionsSentResult;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

  @Autowired
  private final MxTransactionInteractor transactionInteractor;

  @GetMapping("/{txHash}/status")
  Mono<TransactionStatus> getTransactionStatus(@PathVariable String txHash) {
    return transactionInteractor.queryTransactionStatus(txHash);
  }

  @GetMapping("/{txHash}")
  Mono<TransactionOnNetwork> queryTransaction(@PathVariable String txHash,
      @RequestParam Boolean withResults) {
    return transactionInteractor.queryTransactionInfo(txHash, withResults)
        .doOnSuccess(r -> {
          // TransactionOnNetwork is an API response which can also be converted to domain object.

          Transaction transaction = r.toDomainObject();
          Address senderAddress = transaction.getSender();
          Address receiverAddress = transaction.getReceiver();
          Balance balance = transaction.getValue();
          Nonce nonce = transaction.getNonce();
          PayloadData data = transaction.getPayloadData();
          GasLimit gasLimit = transaction.getGasLimit();

          // ... and others
        });
  }


  @PostMapping(value = "/send")
  Mono<TransactionHash> sendTransaction(@RequestPart Mono<FilePart> pemFile,
      @RequestPart TransactionDto transaction) {
//        The following approach is used when we need a more configurable way to send transactions.
//        The same can be done with sending multiple, estimating and simulating.
//
//        return pemFile
//                .flatMap(Wallet::fromPemFile)
//                .flatMap(wallet ->  {
//
//                    transactionInteractor.sendTransaction(wallet, transaction.mapToRequest());
//
//                    var transactionObj = new Transaction();
//
//                    transactionObj.setChainID(ChainID.DEVNET);
//                    transactionObj.setReceiver(Address.fromBech32(transaction.getReceiverAddress()));
//                    transactionObj.setSender(Address.fromHex(wallet.getPublicKeyHex()));
//                    transactionObj.setNonce(Nonce.fromLong(245L));
//                    transactionObj.setGasLimit(GasLimit.fromString("70000"));
//                    transactionObj.setValue(Balance.fromString(CurrencyConstants.ONE_EGLD_STRING));
//                    transactionObj.setGasPrice(GasPrice.fromString("0000000001"));
//                    transactionObj.setVersion(TransactionVersion.withTransactionHashSignVersion());
//                    ... and others;
//
//                    wallet.sign(transactionObj);
//
//                    var sendable = transactionObj.toSendable();
//
//                    return transactionInteractor.sendTransaction(sendable);
//                });

    // For a simpler approach to transaction sending, we can directly use the overloaded methods
    // which takes care of the complexities.

    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> transactionInteractor.sendTransaction(wallet, transaction.mapToRequest()));
  }

  @PostMapping(value = "/send-multiple")
  Mono<TransactionsSentResult> sendMultipleTransactions(@RequestPart Mono<FilePart> pemFile,
      @RequestPart List<TransactionDto> transactions) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> transactionInteractor.sendMultipleTransactions(
            wallet,
            transactions.stream()
                .map(TransactionDto::mapToRequest)
                .collect(Collectors.toList())));
  }

  @PostMapping("/simulate")
  Mono<SimulationResults> simulateTransaction(@RequestPart Mono<FilePart> pemFile,
      @RequestPart TransactionDto transaction) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> transactionInteractor.simulateTransaction(wallet, transaction.mapToRequest()));
  }

  @GetMapping("/cost")
  Mono<TransactionCostEstimation> estimateTransactionCost(@RequestPart Mono<FilePart> pemFile,
      @RequestPart TransactionDto transaction) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> transactionInteractor.estimateTransactionCost(wallet, transaction.mapToRequest()));
  }

}
