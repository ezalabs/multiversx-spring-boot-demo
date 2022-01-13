package software.crldev.elrondspringbootdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.model.NetworkConfig;
import software.crldev.elrondspringbootstarterreactive.api.model.NodeHeartbeatStatus;
import software.crldev.elrondspringbootstarterreactive.api.model.ShardStatus;
import software.crldev.elrondspringbootstarterreactive.interactor.network.ErdNetworkInteractor;

import java.util.List;

@RestController
@AllArgsConstructor
public class NetworkController {

    @Autowired
    private final ErdNetworkInteractor networkInteractor;

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
