package io.ezalabs.multiversxspringbootdemo.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootdemo.dto.FunctionDto;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultHex;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultInt;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultString;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionHash;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractor;

@RestController
@AllArgsConstructor
@RequestMapping("/smart-contract")
public class SmartContractController {

  @Autowired
  private final MxSmartContractInteractor interactor;

  @PostMapping("/function")
  Mono<TransactionHash> callFunction(@RequestPart Mono<FilePart> pemFile,
      @RequestPart FunctionDto function) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> interactor.callFunction(wallet, function.mapFunction()));

  }

  @PostMapping("/query")
  Mono<ContractQueryResult> query(@RequestPart Mono<FilePart> pemFile,
      @RequestPart FunctionDto query) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> interactor.query(query.mapQuery(wallet)));

  }

  @PostMapping("/query-hex")
  Mono<ContractQueryResultHex> queryHex(@RequestPart Mono<FilePart> pemFile,
      @RequestPart FunctionDto query) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> interactor.queryHex(query.mapQuery(wallet)));
  }

  @PostMapping("/query-string")
  Mono<ContractQueryResultString> queryString(@RequestPart Mono<FilePart> pemFile,
      @RequestPart FunctionDto query) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> interactor.queryString(query.mapQuery(wallet)));
  }

  @PostMapping("/query-int")
  Mono<ContractQueryResultInt> queryInt(@RequestPart Mono<FilePart> pemFile,
      @RequestPart FunctionDto query) {
    return pemFile
        .flatMap(Wallet::fromPemFile)
        .flatMap(wallet -> interactor.queryInt(query.mapQuery(wallet)));
  }

}
