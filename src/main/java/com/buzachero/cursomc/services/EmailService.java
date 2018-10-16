package com.buzachero.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.buzachero.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
