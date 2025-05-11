package com.lab.ms_notificacao.service.impl;

import com.lab.ms_notificacao.service.MessageTextService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSMSService implements MessageTextService {

    private static final Logger log = LoggerFactory.getLogger(TwilioSMSService.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.account.auth-token}")
    private String accountAuthToken;

    @Value("${twilio.account.phone}")
    private String phone;

    @Override
    public void sendSMS(String para, String mensagem) {
        try {
            Twilio.init(accountSid, accountAuthToken);
            enviaMensagem(para, mensagem);
            log.info("SMS enviado para {} com sucesso.", para);
        } catch (Exception ex) {
            log.error("Error ao enviar SMS : {}", ex.toString());
        }
    }

    private void enviaMensagem(String para, String mensagem) {
        PhoneNumber destino = new PhoneNumber(para);
        PhoneNumber numeroTwilio = new PhoneNumber(phone);
        Message.creator(destino, numeroTwilio, mensagem).create();
    }
}
