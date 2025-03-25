package com.lab.ms_notificacao;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsNotificacaoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MsNotificacaoApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            final var token = System.getenv("TWILIO_AUTH_TOKEN");
            final var sid = System.getenv("TWILIO_SID");
            System.out.println("TOKEN : " + token);
            System.out.println("SID: " + sid);
        };
    }
}
