package com.sostenesantunes.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.sostenesantunes.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
