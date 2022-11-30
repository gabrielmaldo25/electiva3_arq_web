package com.proyecto.electiva3.backend.captcha;

import cn.apiclub.captcha.Captcha;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {
    String valor = "";
    Boolean flag = true;
    public CaptchaSettings genCaptcha() {

        CaptchaSettings captchaSettings = new CaptchaSettings();
        Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
        captchaSettings.setHiddenCaptcha(captcha.getAnswer());
        captchaSettings.setCaptcha("");
        captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(captcha));

        // debido al doble request del autentication
        if(flag) {
            this.valor = captcha.getAnswer();
        }

        flag = !flag;

        return captchaSettings;
    }

    public String getValor() {
        return valor;
    }
}
