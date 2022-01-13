package software.crldev.elrondspringbootdemo.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootdemo.dto.FunctionDto;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.smartcontract.ErdSmartContractInteractor;

@RestController
@AllArgsConstructor
@RequestMapping("/smart-contract")
public class SmartContractController {

    @Autowired
    private final ErdSmartContractInteractor interactor;

    @PostMapping("/function")
    Mono<TransactionHash> callFunction(@RequestPart Mono<FilePart> pemFile,
                                       @RequestPart FunctionDto function) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> interactor.callFunction(wallet, function.mapFunction()));

    }

    @PostMapping("/query")
    Mono<ScQueryResult> query(@RequestPart Mono<FilePart> pemFile,
                              @RequestPart FunctionDto query) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> interactor.query(wallet, query.mapQuery(wallet)));

    }

    @PostMapping("/query-hex")
    Mono<ScQueryResultHex> queryHex(@RequestPart Mono<FilePart> pemFile,
                                    @RequestPart FunctionDto query) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> interactor.queryHex(wallet, query.mapQuery(wallet)));
    }

    @PostMapping("/query-string")
    Mono<ScQueryResultString> queryString(@RequestPart Mono<FilePart> pemFile,
                                          @RequestPart FunctionDto query) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> interactor.queryString(wallet, query.mapQuery(wallet)));
    }

    @PostMapping("/query-int")
    Mono<ScQueryResultInt> queryInt(@RequestPart Mono<FilePart> pemFile,
                                    @RequestPart FunctionDto query) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> interactor.queryInt(wallet, query.mapQuery(wallet)));
    }

}
