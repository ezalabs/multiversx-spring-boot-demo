package software.crldev.elrondspringbootdemo.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTGlobalOpDto;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTIssuanceDto;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTLocalOpDto;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTMultiTransferDto;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTOwnershipTransferDto;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTRoleAssignmentDto;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTTransferDto;
import software.crldev.elrondspringbootdemo.dto.esdt.ESDTUpgradeDto;
import software.crldev.elrondspringbootdemo.dto.esdt.NFTAttributesDto;
import software.crldev.elrondspringbootdemo.dto.esdt.NFTCreateDto;
import software.crldev.elrondspringbootdemo.dto.esdt.NFTGlobalOpDto;
import software.crldev.elrondspringbootdemo.dto.esdt.NFTLocalOpDto;
import software.crldev.elrondspringbootdemo.dto.esdt.NFTRoleTransferDto;
import software.crldev.elrondspringbootdemo.dto.esdt.NFTUrisDto;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountESDTRoles;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import software.crldev.multiversxspringbootstarterreactive.api.model.ESDTToken;
import software.crldev.multiversxspringbootstarterreactive.api.model.NFTData;
import software.crldev.multiversxspringbootstarterreactive.api.model.TokenList;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTQueryType;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.NFTStopCreation;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.esdt.MxESDTInteractor;

@RestController
@AllArgsConstructor
@RequestMapping("/esdt")
public class ESDTController {

    private final MxESDTInteractor esdtInteractor;

    @PostMapping("/issuance")
    Mono<TransactionHash> issueEsdt(@RequestPart Mono<FilePart> pemFile,
                                    @RequestPart ESDTIssuanceDto issuance) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, issuance.mapToTransaction()));
    }

    @PostMapping("/transfer")
    Mono<TransactionHash> transferEsdt(@RequestPart Mono<FilePart> pemFile,
                                       @RequestPart ESDTTransferDto transfer) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, transfer.mapToTransaction()));
    }

    @PostMapping("/multi-transfer")
    Mono<TransactionHash> multiTransferEsdt(@RequestPart Mono<FilePart> pemFile,
                                            @RequestPart ESDTMultiTransferDto transfer) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, transfer.mapToTransaction()));
    }

    @PostMapping("/local-op")
    Mono<TransactionHash> localOp(@RequestPart Mono<FilePart> pemFile,
                                  @RequestPart ESDTLocalOpDto localOp) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, localOp.mapToTransaction()));
    }

    @PostMapping("/global-op")
    Mono<TransactionHash> globalOp(@RequestPart Mono<FilePart> pemFile,
                                   @RequestPart ESDTGlobalOpDto globalOp) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, globalOp.mapToTransaction()));
    }

    @PostMapping("/role-assign")
    Mono<TransactionHash> role(@RequestPart Mono<FilePart> pemFile,
                               @RequestPart ESDTRoleAssignmentDto roleAssignment) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, roleAssignment.mapToTransaction()));
    }

    @PostMapping("/transfer-ownership")
    Mono<TransactionHash> ownership(@RequestPart Mono<FilePart> pemFile,
                                    @RequestPart ESDTOwnershipTransferDto ownershipTransfer) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, ownershipTransfer.mapToTransaction()));
    }

    @PostMapping("/upgrade")
    Mono<TransactionHash> upgrade(@RequestPart Mono<FilePart> pemFile,
                                  @RequestPart ESDTUpgradeDto upgrade) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, upgrade.mapToTransaction()));
    }

    @PostMapping("/nft/create")
    Mono<TransactionHash> nftCreate(@RequestPart Mono<FilePart> pemFile,
                                    @RequestPart NFTCreateDto nft) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, nft.mapToTransaction()));
    }

    @PostMapping("/nft/transfer-role")
    Mono<TransactionHash> nftTransferRole(@RequestPart Mono<FilePart> pemFile,
                                          @RequestPart NFTRoleTransferDto transfer) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, transfer.mapToTransaction()));
    }

    @PostMapping("/nft/update-attributes")
    Mono<TransactionHash> nftUpdateAttributes(@RequestPart Mono<FilePart> pemFile,
                                              @RequestPart NFTAttributesDto update) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, update.mapToTransaction()));
    }

    @PostMapping("/nft/add-uri")
    Mono<TransactionHash> nftAddUri(@RequestPart Mono<FilePart> pemFile,
                                    @RequestPart NFTUrisDto addUri) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, addUri.mapToTransaction()));
    }

    @PostMapping("/nft/local-op")
    Mono<TransactionHash> nftLocalOp(@RequestPart Mono<FilePart> pemFile,
                                     @RequestPart NFTLocalOpDto localOp) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, localOp.mapToTransaction()));
    }

    @PostMapping("/nft/global-op")
    Mono<TransactionHash> nftGlobalOp(@RequestPart Mono<FilePart> pemFile,
                                      @RequestPart NFTGlobalOpDto globalOp) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, globalOp.mapToTransaction()));
    }

    @PostMapping("/nft/stop-creation/{identifier}")
    Mono<TransactionHash> stopNftCreation(@PathVariable String identifier, @RequestPart Mono<FilePart> pemFile) {
        return pemFile
                .flatMap(Wallet::fromPemFile)
                .flatMap(wallet -> esdtInteractor.processEsdtTransaction(wallet, NFTStopCreation.builder()
                        .tokenIdentifier(TokenIdentifier.fromString(identifier)).build()));
    }

    @GetMapping("/address/{address}")
    Mono<Set<ESDTToken>> getTokensForAccount(@PathVariable String address) {
        return esdtInteractor.getTokensForAccount(Address.fromBech32(address));
    }

    @GetMapping("/tokens")
    Mono<TokenList> getAllEsdtTokens() {
        return esdtInteractor.getAllTokens(ESDTQueryType.ALL);
    }

    @GetMapping("/tokens/fungible")
    Mono<TokenList> getAllEsdtFungibleTokens() {
        return esdtInteractor.getAllTokens(ESDTQueryType.FUNGIBLE);
    }

    @GetMapping("/tokens/non-fungible")
    Mono<TokenList> getAllNftTokens() {
        return esdtInteractor.getAllTokens(ESDTQueryType.NON_FUNGIBLE);
    }

    @GetMapping("/tokens/semi-fungible")
    Mono<TokenList> getAllSftTokens() {
        return esdtInteractor.getAllTokens(ESDTQueryType.SEMI_FUNGIBLE);
    }

    @GetMapping("/properties/{tokenIdentifier}")
    Mono<List<String>> getTokenProperties(@PathVariable String tokenIdentifier) {
        return esdtInteractor.getTokenProperties(TokenIdentifier.fromString(tokenIdentifier))
                .map(ContractQueryResult::getData);
    }

    @GetMapping("/special-roles/{tokenIdentifier}")
    Mono<Map<String, Set<String>>> getTokenSpecialRoles(@PathVariable String tokenIdentifier) {
        return esdtInteractor.getTokenSpecialRoles(TokenIdentifier.fromString(tokenIdentifier))
                .map(ContractQueryResult::getData)
                .map(this::processRolesResult);
    }

    @GetMapping("/nft/data/{address}/{identifier}/{nonce}")
    Mono<NFTData> getNftData(@PathVariable String address, @PathVariable String identifier, @PathVariable Long nonce) {
        return esdtInteractor.getNftDataForAccount(Address.fromBech32(address),
                TokenIdentifier.fromString(identifier), Nonce.fromLong(nonce));
    }

    @GetMapping("/address/{address}/nfts")
    Mono<TokenList> getNftSft(@PathVariable String address) {
        return esdtInteractor.getNftSftForAccount(Address.fromBech32(address));
    }

    @GetMapping("/address/{address}/role/{role}")
    Mono<TokenList> getTokensForRole(@PathVariable String address, @PathVariable String role) {
        return esdtInteractor.getTokensWithRole(Address.fromBech32(address), ESDTSpecialRole.getByValue(role));
    }

    @GetMapping("/address/{address}/roles")
    Mono<AccountESDTRoles> getRolesForAccount(@PathVariable String address) {
        return esdtInteractor.getTokenRolesForAccount(Address.fromBech32(address));
    }

    private Map<String, Set<String>> processRolesResult(List<String> result) {
        var rolesMap = new HashMap<String, Set<String>>();
        var decoder = Base64.getDecoder();
        result.stream()
                .map(decoder::decode)
                .map(String::new)
                .map(r -> r.split(":"))
                .forEach(r -> {
                    var address = r[0];
                    var roles = Set.of(r[1].split(","));
                    rolesMap.put(address, roles);
                });

        return rolesMap;
    }
}
