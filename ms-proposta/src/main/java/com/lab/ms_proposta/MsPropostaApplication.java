package com.lab.ms_proposta;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsPropostaApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MsPropostaApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
