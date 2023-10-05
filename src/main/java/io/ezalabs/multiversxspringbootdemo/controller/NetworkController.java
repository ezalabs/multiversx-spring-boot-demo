package io.ezalabs.multiversxspringbootdemo.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.NetworkConfig;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.NodeHeartbeatStatus;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ShardStatus;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.network.MxNetworkInteractor;

@RestController
@AllArgsConstructor
public class NetworkController {

    @Autowired
    private final MxNetworkInteractor networkInteractor;

    @GetMapping("/network/config")
    public Mono<NetworkConfig> getNetworkConfig() {
        return networkInteractor.getNetworkConfig();
    }

    @GetMapping("/network/status/{shardId}")
    public Mono<ShardStatus> getShardStatus(@PathVariable String shardId) {
        return networkInteractor.getShardStatus(shardId);
    }

    @GetMapping("/node/heartbeatstatus")
    Mono<List<NodeHeartbeatStatus>> getNodeHeartbeatStatus() {
        return networkInteractor.getNodeHeartbeatStatus();
    }

}
