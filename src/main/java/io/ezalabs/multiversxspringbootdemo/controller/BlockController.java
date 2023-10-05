package io.ezalabs.multiversxspringbootdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.Hyperblock;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ShardBlock;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.block.MxBlockInteractor;

@RestController
@AllArgsConstructor
public class BlockController {

    @Autowired
    private final MxBlockInteractor blockInteractor;

    @GetMapping("/hyperblock/by-nonce/{nonce}")
    public Mono<Hyperblock> getHyperblockByNonce(@PathVariable Long nonce) {
        return blockInteractor.queryHyperblockByNonce(nonce);
    }

    @GetMapping("/hyperblock/by-hash/{hash}")
    public Mono<Hyperblock> getHyperblockByHash(@PathVariable String hash) {
        return blockInteractor.queryHyperblockByHash(hash);
    }

    @GetMapping("/block/{shard}/by-nonce/{nonce}")
    public Mono<ShardBlock> getBlockByNonceFromShard(@PathVariable Integer shard, @PathVariable Long nonce) {
        return blockInteractor.queryShardBlockByNonceFromShard(shard, nonce);
    }

    @GetMapping("/block/{shard}/by-hash/{hash}")
    public Mono<ShardBlock> getBlockByHashFromShard(@PathVariable Integer shard, @PathVariable String hash) {
        return blockInteractor.queryShardBlockByHashFromShard(shard, hash);
    }

}
