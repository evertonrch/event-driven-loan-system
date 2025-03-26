package com.lab.ms_analise_credito;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsAnaliseCreditoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MsAnaliseCreditoApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
