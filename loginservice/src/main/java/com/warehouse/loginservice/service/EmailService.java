package com.warehouse.loginservice.service;

import com.warehouse.loginservice.dto.MailBody;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendSimpleMessage(MailBody mailBody);

}
