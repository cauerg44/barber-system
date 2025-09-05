package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final AppointmentRepository appointmentRepository;

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender, AppointmentRepository appointmentRepository) {
        this.javaMailSender = javaMailSender;
        this.appointmentRepository = appointmentRepository;
    }

    @Value("${spring.mail.username}")
    private String from;

    @Value("${app.mail.to}")
    private String to;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void sendReportByEmail() {
        String date = LocalDate.now().format(dateFormatter);
        String moment = LocalDateTime.now().format(dateTimeFormatter);

        BigDecimal totalJunior = appointmentRepository.getTotalProfitForBarber(1L);
        BigDecimal totalAllan = appointmentRepository.getTotalProfitForBarber(2L);
        BigDecimal totalDaniel = appointmentRepository.getTotalProfitForBarber(3L);

        BigDecimal totalPix = appointmentRepository.getTotalProfitForPayment(1L);
        BigDecimal totalDebit = appointmentRepository.getTotalProfitForPayment(2L);
        BigDecimal totalCredit = appointmentRepository.getTotalProfitForPayment(3L);
        BigDecimal totalCash = appointmentRepository.getTotalProfitForPayment(4L);
        BigDecimal totalProfit = appointmentRepository.getTotalProfit();

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject("RELATÓRIO DE LUCRO OBTIDO - " + moment);

            String text = String.format("""
                            💈 Relatório de Lucro - %s
                            
                            💇‍♂️ Totais por Barbeiro:
                            - Júnior: R$ %s
                            - Allan: R$ %s
                            - Daniel: R$ %s
                            
                            💰 Totais por Forma de Pagamento:
                            - ⚡🏦 PIX: R$ %s
                            - 💳 Débito: R$ %s
                            - 💳 Crédito: R$ %s
                            - 💵 Dinheiro: R$ %s
                            
                            📊 Total Geral: R$ %s
                            """,
                    date, totalJunior,
                    totalAllan, totalDaniel,
                    totalPix, totalDebit,
                    totalCredit, totalCash,
                    totalProfit
            );


            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}