package software.crldev.elrondspringbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ElrondSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElrondSpringBootDemoApplication.class, args);
    }

}
