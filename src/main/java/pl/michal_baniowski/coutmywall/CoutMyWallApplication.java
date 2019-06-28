package pl.michal_baniowski.coutmywall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CoutMyWallApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CoutMyWallApplication.class, args);
    }

}
