package com.lab.ms_notificacao;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsNotificacaoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MsNotificacaoApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
