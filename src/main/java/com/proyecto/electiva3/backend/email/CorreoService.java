package com.proyecto.electiva3.backend.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class CorreoService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration config;

    private String from = "roshkaprueba@gmail.com";

    public void enviar_correo_ganador(String to, Map<String, Object> model) {
        sendEmailWithHTML(to, "Ganador de Sorteo", model, "email_sorteo.ftl");
    }

    public void notificar_cliente(String to, Map<String, Object> model) {
        sendEmailWithHTML(to, "Vencimientos de puntos", model, "email_venc_puntos.ftl");
    }

    private void sendEmailWithHTML(String to, String subject, Map<String, Object> model, String path) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
            //helper.addAttachment("roshkagram.png", new ClassPathResource("static/img/roshkagram.png"));

            Template t = config.getTemplate(path);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(to);
            helper.setText(html, true);
            helper.setSubject(subject);
            helper.setFrom(from);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            sendEmail(to, subject, model.get("text").toString());
        }
    }

    /* envia un correo solo con el link de activacion */
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from); // remitente
        message.setTo(to); // destinatario
        message.setSubject(subject); // titulo
        message.setText(text); // cuerpo

        mailSender.send(message);
    }

}
