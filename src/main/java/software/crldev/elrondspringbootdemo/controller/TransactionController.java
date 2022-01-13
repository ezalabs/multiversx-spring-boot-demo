package software.crldev.elrondspringbootdemo.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootdemo.dto.TransactionDto;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private final ErdTransactionInteractor transactionInteractor;

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
